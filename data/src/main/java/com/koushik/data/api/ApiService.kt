package com.koushik.data.api

import com.koushik.data.model.Item
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("latest")
    suspend fun getItems(
        @Query("apikey") apiKey: String
    ): Response<BaseResponse<List<Item>>>
}