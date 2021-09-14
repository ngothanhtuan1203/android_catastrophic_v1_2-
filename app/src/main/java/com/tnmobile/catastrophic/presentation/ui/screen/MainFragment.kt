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
import androidx.recyclerview.widget.LinearLayoutManager
import com.tnmobile.catastrophic.R
import com.tnmobile.catastrophic.domain.model.Cat
import com.tnmobile.catastrophic.domain.model.News
import com.tnmobile.catastrophic.presentation.ui.adapter.CatAdapter
import com.tnmobile.catastrophic.presentation.ui.adapter.LoaderStateAdapter
import com.tnmobile.catastrophic.presentation.ui.adapter.NewsAdapter
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

    lateinit var newsAdapter: NewsAdapter
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

        viewModel.loadNewsData()
    }

    //ui
    private fun setupUI(root: View) {
        adapter = CatAdapter()
        newsAdapter = NewsAdapter(emptyList())
//        loaderStateAdapter = LoaderStateAdapter { newsAdapter.retry() }
        val mNoOfColumns = Util.calculateNoOfColumns(this.requireContext(), 135f)
        root.recyclerView.layoutManager = LinearLayoutManager(this.context)
        root.recyclerView.adapter = newsAdapter
//        root.recyclerView.adapter = newsAdapter.withLoadStateFooter(loaderStateAdapter)


    }


    //view model
    private fun setupViewModel() {
        viewModel.cats.observe(viewLifecycleOwner, renderCats)
        viewModel.news.observe(viewLifecycleOwner, renderNews)
    }

    private val renderNews = Observer<List<News>> {
        layoutError.visibility = View.GONE
        layoutEmpty.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        lifecycleScope.launch {
            newsAdapter.update(it)
            newsAdapter.notifyDataSetChanged()
        }
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