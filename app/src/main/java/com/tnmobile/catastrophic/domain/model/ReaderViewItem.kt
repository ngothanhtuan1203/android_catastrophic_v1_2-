package com.tnmobile.catastrophic.domain.model

data class ReaderViewItem(
    val author: String = "",
    val publishDate: String = "",
    val title: String = "",
    val subTitle: String = "",
    val category: String = "",
    val coverImgUrl: String = "",
    val originalUrl: String = "",
    val contents: List<Content> = arrayListOf()
)
