package com.tnmobile.catastrophic.presentation.ui.adapter

import android.view.View
import com.tnmobile.catastrophic.R
import com.tnmobile.catastrophic.domain.model.Cat
import com.tnmobile.catastrophic.presentation.ui.base.BaseAdapter

class CatAdapter(private var cats: List<Cat>) :
    BaseAdapter<Cat, CatViewHolder>(cats, R.layout.item_row_cat) {

    override fun initViewHolder(rootView: View): CatViewHolder {
        return CatViewHolder(rootView)
    }

}