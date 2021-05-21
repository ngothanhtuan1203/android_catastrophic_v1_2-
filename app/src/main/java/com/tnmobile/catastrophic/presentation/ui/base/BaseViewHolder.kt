package com.tnmobile.catastrophic.presentation.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
   abstract suspend fun bind(data: T)
}