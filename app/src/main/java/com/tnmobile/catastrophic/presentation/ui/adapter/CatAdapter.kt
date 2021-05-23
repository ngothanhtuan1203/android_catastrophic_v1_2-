package com.tnmobile.catastrophic.presentation.ui.adapter

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.tnmobile.catastrophic.R
import com.tnmobile.catastrophic.domain.model.Cat
import com.tnmobile.catastrophic.presentation.ui.base.BasePagingAdapter

class CatAdapter : BasePagingAdapter<Cat, CatViewHolder>(
    R.layout.item_row_cat,
    REPO_COMPARATOR
) {

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Cat>() {
            override fun areItemsTheSame(oldItem: Cat, newItem: Cat) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Cat, newItem: Cat) =
                oldItem.id == newItem.id
        }
    }

    override fun initViewHolder(rootView: View): CatViewHolder {
        return CatViewHolder(rootView)
    }


}