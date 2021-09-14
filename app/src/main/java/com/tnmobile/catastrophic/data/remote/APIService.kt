package com.tnmobile.catastrophic.data.remote

import com.tnmobile.catastrophic.data.remote.entity.CatDto
import com.tnmobile.catastrophic.domain.model.News
import retrofit2.http.GET
import retrofit2.http.Query


interface APIService {
    @GET("/v1/images/search?mime_types=png&&order=Desc")
    suspend fun fetchCatAPI(@Query("page") page: Int, @Query("limit") limit: Int): List<CatDto>
    @GET("https://itao.info/api/data.json")
    suspend fun fetchNews():List<News>
}