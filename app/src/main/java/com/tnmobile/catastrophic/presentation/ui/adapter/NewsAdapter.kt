package com.tnmobile.catastrophic.presentation.ui.adapter

import android.view.View
import com.tnmobile.catastrophic.R
import com.tnmobile.catastrophic.domain.model.News
import com.tnmobile.catastrophic.presentation.ui.base.BaseAdapter

class NewsAdapter(datas:List<News>) : BaseAdapter<News, NewsViewHolder>(
    datas ,
    R.layout.item_row_news
) {

    override fun initViewHolder(rootView: View): NewsViewHolder {
        return NewsViewHolder(rootView)
    }


}