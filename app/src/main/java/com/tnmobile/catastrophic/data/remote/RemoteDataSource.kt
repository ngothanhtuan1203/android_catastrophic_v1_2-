package com.tnmobile.catastrophic.data.remote

import com.tnmobile.catastrophic.data.remote.entity.CatDto
import com.tnmobile.catastrophic.domain.model.News
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    suspend fun fetchCats(page: Int, limit: Int): Flow<List<CatDto>>
    suspend fun fetchNews(): Flow<List<News>>
}