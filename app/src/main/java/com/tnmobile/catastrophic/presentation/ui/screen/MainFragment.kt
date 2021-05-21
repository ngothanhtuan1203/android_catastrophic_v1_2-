package com.tnmobile.catastrophic.presentation.ui.screen

import android.os.Bundle
import android.view.*
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.tnmobile.catastrophic.R
import com.tnmobile.catastrophic.domain.model.Cat
import com.tnmobile.catastrophic.presentation.ui.adapter.CatAdapter
import com.tnmobile.catastrophic.utilily.TNLog
import com.tnmobile.catastrophic.utilily.Util
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_error.*
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment.view.*


@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: CatAdapter
    private lateinit var mRootView: View

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.main_fragment, container, false)
        TNLog.d("MainFragment", "DEBUG onCreateView")
        mRootView = root
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setupViewModel()
        setupUI(mRootView)

        TNLog.d("MainFragment", "DEBUG onActivityCreated")

        viewModel.loadCatsData()
    }

    //ui
    private fun setupUI(root: View) {
        adapter = CatAdapter(
                viewModel.cats.value ?: emptyList()
        )
        val mNoOfColumns = Util.calculateNoOfColumns(this.context!!, 135f)
        root.recyclerView.layoutManager = GridLayoutManager(this.context, mNoOfColumns)
        root.recyclerView.adapter = adapter

        root.swipe_layout.setOnRefreshListener {
            swipe_layout.isRefreshing = false
            viewModel.loadCatsData()
        }


    }

    //view model
    private fun setupViewModel() {
        viewModel.cats.observe(viewLifecycleOwner, renderArticles)
        viewModel.isViewLoading.observe(viewLifecycleOwner, isViewLoadingObserver)
        viewModel.onMessageError.observe(viewLifecycleOwner, onMessageErrorObserver)
        viewModel.isEmptyList.observe(viewLifecycleOwner, emptyListObserver)
    }

    private val renderArticles = Observer<List<Cat>> {
        //TNLog.d(TAG, "data updated $it")
        layoutError.visibility = View.GONE
        layoutEmpty.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        adapter.update(it)
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        //TNLog.v(TAG, "isViewLoading $it")
        val visibility = if (it) View.VISIBLE else View.GONE
        progressBar.visibility = visibility
    }

    private val onMessageErrorObserver = Observer<Any> {
        //TNLog.v(TAG, "onMessageError $it")
        layoutError.visibility = View.VISIBLE
        layoutEmpty.visibility = View.GONE
        textViewError.text = "Error $it"
        recyclerView.visibility = View.GONE
    }

    private val emptyListObserver = Observer<Boolean> {
        //TNLog.v(TAG, "emptyListObserver $it")
        layoutEmpty.visibility = View.VISIBLE
        layoutError.visibility = View.GONE
        recyclerView.visibility = View.GONE

    }
}