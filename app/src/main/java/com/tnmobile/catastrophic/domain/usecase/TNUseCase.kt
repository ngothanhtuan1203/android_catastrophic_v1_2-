package com.tnmobile.catastrophic.domain.usecase

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.tnmobile.catastrophic.domain.model.Cat
import com.tnmobile.catastrophic.domain.model.News
import com.tnmobile.catastrophic.domain.model.ReaderViewItem
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
interface TNUseCase {
    suspend fun getAllCats(): Flow<PagingData<Cat>>

    suspend fun getAllNews(): Flow<List<News>>

    suspend fun parseData(url:String):Flow<ReaderViewItem?>
}