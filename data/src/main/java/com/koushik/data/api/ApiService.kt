package com.koushik.data.api

import com.koushik.core.util.Constants
import com.koushik.data.model.ItemDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(Constants.API.LATEST_NEWS_ENDPOINT)
    suspend fun getItems(
        @Query("apikey") apiKey: String
    ): Response<BaseResponse<List<ItemDto>>>
}