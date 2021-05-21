package com.tnmobile.catastrophic.data.local.room

import androidx.room.*
import com.tnmobile.catastrophic.data.local.entity.CatEntity

@Dao
interface CatDao {
    @Query("SELECT * FROM notes")
    suspend fun getAllCats(): List<CatEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCat(notes: CatEntity)

    @Update
    suspend fun updateCats(notes: CatEntity)
}