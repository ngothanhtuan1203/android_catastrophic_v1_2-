package com.tnmobile.catastrophic.domain.repository

import com.tnmobile.catastrophic.data.remote.RemoteDataSource
import com.tnmobile.catastrophic.data.remote.entity.CatDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class RemoteRepository @Inject constructor(private val remoteDataSource: RemoteDataSource) {
    suspend fun getCats(): Flow<List<CatDto>> {
      return remoteDataSource.fetchCats()
    }

}