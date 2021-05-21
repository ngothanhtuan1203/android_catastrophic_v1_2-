package com.tnmobile.catastrophic.data.local

import com.tnmobile.catastrophic.data.local.LocalDataSource
import com.tnmobile.catastrophic.data.local.room.CatDao
import com.tnmobile.catastrophic.data.local.entity.CatEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val noteDao: CatDao) : LocalDataSource {

    override suspend fun insertCat(title: String, detail: String) {
        val noteEntity = CatEntity(title, detail)
        return  noteDao.insertCat(noteEntity)
    }

    override suspend fun fetchCats(): Flow<List<CatEntity>> = flow {
        val datas = noteDao.getAllCats()
        emit(datas)
    }

}