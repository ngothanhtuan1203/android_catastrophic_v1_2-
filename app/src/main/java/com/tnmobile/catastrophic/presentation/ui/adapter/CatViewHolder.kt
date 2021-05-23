package com.tnmobile.catastrophic.presentation.ui.adapter

import android.view.View
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import coil.api.load
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.tnmobile.catastrophic.R
import com.tnmobile.catastrophic.domain.model.Cat
import com.tnmobile.catastrophic.presentation.ui.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_row_cat.view.*

class CatViewHolder(view: View) : BaseViewHolder<Cat>(view) {

    private val thumbImg: ImageView = view.cat_imageView


    override fun bind(data: Cat?) {
        if (data != null) {
            thumbImg.load(data.url) {
                crossfade(true)
                crossfade(1000)
                transformations(RoundedCornersTransformation())
                scale(Scale.FILL)
            }
            itemView.setOnClickListener {
                val bundle = bundleOf("url" to data.url)
                itemView.findNavController()
                    .navigate(R.id.action_navigation_main_to_DetailActivity, bundle)
            }
        }
    }

}