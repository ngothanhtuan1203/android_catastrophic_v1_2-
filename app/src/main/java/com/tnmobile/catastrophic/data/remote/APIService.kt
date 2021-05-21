package com.tnmobile.catastrophic.data.remote

import com.tnmobile.catastrophic.data.remote.entity.CatDto
import retrofit2.http.GET


interface APIService {
    @GET("/v1/images/search?limit=30&page=1&mime_types=png&&order=Desc")
    suspend fun fetchCatAPI(): List<CatDto>
}