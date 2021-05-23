package com.tnmobile.catastrophic.data.local


import com.tnmobile.catastrophic.data.local.entity.CatEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun insertCat(title: String, detail: String)
    suspend fun fetchCats(): Flow<List<CatEntity>>
}