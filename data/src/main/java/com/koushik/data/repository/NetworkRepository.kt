package com.koushik.data.repository

import com.koushik.core.util.Constants
import com.koushik.data.api.ApiService
import com.koushik.data.mapper.toDomainModel
import com.koushik.domain.model.Item
import com.koushik.domain.repository.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val apiService: ApiService
) : ItemRepository {

    override suspend fun fetchItems(): Result<List<Item>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getItems(
                    apiKey = Constants.API_KEY
                )

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.status == "success" && body.results != null) {
                        // Map the list of ItemDto to the domain model Item
                        val items = body.results.map { it.toDomainModel() }
                        Result.success(items)
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