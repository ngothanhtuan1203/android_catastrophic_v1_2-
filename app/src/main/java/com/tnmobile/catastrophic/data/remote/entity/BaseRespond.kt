package com.tnmobile.catastrophic.data.remote.entity

data class BaseRespond<T>(
    val data: T?,
    val message: String,
    val isSuccess: Boolean
)