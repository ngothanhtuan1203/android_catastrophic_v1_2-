package com.tnmobile.catastrophic.presentation.ui.adapter

import android.view.View
import android.widget.ImageView
import coil.api.load
import coil.transform.RoundedCornersTransformation
import com.tnmobile.catastrophic.domain.model.Cat
import com.tnmobile.catastrophic.presentation.ui.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_row_cat.view.*

class CatViewHolder(view: View) : BaseViewHolder<Cat>(view) {

    private val thumbImg: ImageView = view.cat_imageView


    override suspend fun bind(cat: Cat) {
        thumbImg.load(cat.url) {
            crossfade(true)
            crossfade(1000)
            transformations(RoundedCornersTransformation(20f))
        }
    }
}