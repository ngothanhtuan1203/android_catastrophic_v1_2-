package com.tnmobile.catastrophic.domain.usecase


interface TNUseCase {
    sealed class Result {
        data class Success<T>(val data: T) : Result()
        data class Failure(val message: String) : Result()
    }

    suspend fun getAllCats(): Result
    suspend fun insertCat(title: String, detail: String)
}