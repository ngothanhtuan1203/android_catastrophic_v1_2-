package com.tnmobile.catastrophic.presentation.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil

abstract class BasePagingAdapter<T : Any, VH : BaseViewHolder<T>>(
    private var viewHolderResourceID: Int,
    diffUtil: DiffUtil.ItemCallback<T>
) :
    PagingDataAdapter<T, VH>(diffUtil) {

    abstract fun initViewHolder(rootView: View): VH

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): VH {
        val rootView = LayoutInflater.from(parent.context)
            .inflate(viewHolderResourceID, parent, false)
        return initViewHolder(
            rootView
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        return holder.bind(data = getItem(position))
    }
}