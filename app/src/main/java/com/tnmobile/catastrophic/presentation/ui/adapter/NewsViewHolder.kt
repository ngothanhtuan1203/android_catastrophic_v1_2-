package com.tnmobile.catastrophic.presentation.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import coil.api.load
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.tnmobile.catastrophic.R
import com.tnmobile.catastrophic.domain.model.News
import com.tnmobile.catastrophic.presentation.ui.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_row_cat.view.cat_imageView
import kotlinx.android.synthetic.main.item_row_news.view.*

class NewsViewHolder(view: View) : BaseViewHolder<News>(view) {

    private val thumbImg: ImageView = view.cat_imageView
    private val title: TextView = view.news_title
    private val postDay: TextView = view.news_postday
    override fun bind(data: News?) {
        if (data != null) {
            thumbImg.load(data.imgUrl) {
                crossfade(true)
                crossfade(1000)
                transformations(RoundedCornersTransformation())
                scale(Scale.FILL)
            }
            title.text = data.title
            postDay.text = data.postDate
            itemView.web_button.setOnClickListener {
                val bundle = bundleOf("url" to data.originalUrl)
                itemView.findNavController()
                    .navigate(R.id.action_navigation_main_to_WebReaderActivity, bundle)
            }

            itemView.native_button.setOnClickListener {
                val bundle = bundleOf("url" to data.originalUrl)
                itemView.findNavController()
                    .navigate(R.id.action_navigation_main_to_SmartView, bundle)
            }
        }
    }

}