package com.tnmobile.catastrophic.domain.repository

import com.tnmobile.catastrophic.data.remote.RemoteDataSource
import com.tnmobile.catastrophic.data.remote.entity.CatDto
import com.tnmobile.catastrophic.domain.model.News
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class RemoteRepository @Inject constructor(private val remoteDataSource: RemoteDataSource) {
    suspend fun getCats(page: Int, limit: Int): Flow<List<CatDto>> {
        return remoteDataSource.fetchCats(page, limit)
    }

    suspend fun getAllNews(): Flow<List<News>> {
        return remoteDataSource.fetchNews();
    }

}