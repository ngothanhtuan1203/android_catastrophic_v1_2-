package com.tnmobile.catastrophic.domain.usecase

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.tnmobile.catastrophic.domain.model.Cat
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
interface TNUseCase {
    suspend fun getAllCats(): Flow<PagingData<Cat>>
}