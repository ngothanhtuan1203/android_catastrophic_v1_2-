package com.tnmobile.catastrophic.presentation.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.tnmobile.catastrophic.R
import com.tnmobile.catastrophic.domain.model.Cat
import com.tnmobile.catastrophic.presentation.ui.adapter.CatAdapter
import com.tnmobile.catastrophic.presentation.ui.adapter.LoaderStateAdapter
import com.tnmobile.catastrophic.utilily.Util
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment.view.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
@ExperimentalPagingApi
class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var mRootView: View

    lateinit var adapter: CatAdapter
    lateinit var loaderStateAdapter: LoaderStateAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.main_fragment, container, false)
        mRootView = root
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setupViewModel()
        setupUI(mRootView)

        viewModel.loadCatsData()
    }

    //ui
    private fun setupUI(root: View) {
        adapter = CatAdapter()
        loaderStateAdapter = LoaderStateAdapter { adapter.retry() }
        val mNoOfColumns = Util.calculateNoOfColumns(this.requireContext(), 135f)
        root.recyclerView.layoutManager = GridLayoutManager(this.context, mNoOfColumns)
        root.recyclerView.adapter = adapter
        root.recyclerView.adapter = adapter.withLoadStateFooter(loaderStateAdapter)


    }


    //view model
    private fun setupViewModel() {
        viewModel.cats.observe(viewLifecycleOwner, renderCats)
    }

    private val renderCats = Observer<PagingData<Cat>> {
        //TNLog.d(TAG, "data updated $it")
        layoutError.visibility = View.GONE
        layoutEmpty.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        lifecycleScope.launch {
            adapter.submitData(it)
        }
    }

}