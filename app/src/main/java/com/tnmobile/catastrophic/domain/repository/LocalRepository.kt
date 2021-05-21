package com.tnmobile.catastrophic.domain.repository

import com.tnmobile.catastrophic.data.local.LocalDataSource
import com.tnmobile.catastrophic.data.local.entity.CatEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalRepository @Inject constructor(private val localDataSource: LocalDataSource) {
    suspend fun insertCat(title: String, detail: String) {
        localDataSource.insertCat(title, detail)
    }

    suspend fun fetchAllCats(): Flow<List<CatEntity>> {
        return localDataSource.fetchCats()
    }
}