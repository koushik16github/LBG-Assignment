package com.koushik.data.repository

import com.koushik.core.util.Constants
import com.koushik.data.api.ApiService
import com.koushik.data.model.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun fetchItems(): Result<List<Item>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getItems(
                    apiKey = Constants.API_KEY
                )
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.status == "success" && body.results != null) {
                        Result.success(body.results)
                    } else {
                        Result.failure(Throwable("Unknown error"))
                    }
                } else {
                    Result.failure(Throwable(response.message()))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}