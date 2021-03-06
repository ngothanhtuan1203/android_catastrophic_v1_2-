package com.tnmobile.catastrophic.data.remote

import com.tnmobile.catastrophic.data.remote.entity.CatDto
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    suspend fun fetchCats(page: Int, limit: Int): Flow<List<CatDto>>
}