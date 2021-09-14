package com.tnmobile.catastrophic.domain.model

import com.tnmobile.catastrophic.utilily.ReadType

data class Content(
    val type: ReadType,
    val rawContent: String,
    val hyperLinks: List<Pair<String, String>>
)
