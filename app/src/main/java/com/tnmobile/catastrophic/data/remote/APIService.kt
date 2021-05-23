package com.tnmobile.catastrophic.data.remote

import com.tnmobile.catastrophic.data.remote.entity.CatDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface APIService {
    @GET("/v1/images/search?mime_types=png&&order=Desc")
    suspend fun fetchCatAPI(@Query("page") page: Int, @Query("limit") limit: Int): List<CatDto>
}