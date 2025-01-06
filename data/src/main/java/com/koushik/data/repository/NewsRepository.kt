package com.koushik.data.repository

import com.koushik.core.util.Constants
import com.koushik.data.api.ApiService
import com.koushik.data.mapper.toDomainModel
import com.koushik.data.util.NetworkResponseHandler
import com.koushik.domain.model.Item
import com.koushik.domain.repository.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val apiService: ApiService
) : ItemRepository {

    override suspend fun fetchItems(): Result<List<Item>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getItems(
                    apiKey = Constants.API_KEY
                )

                NetworkResponseHandler.handleResponse(response) { body ->
                    if (body.status == "success" && body.results != null) {
                        body.results.map { it.toDomainModel() }
                    } else {
                        throw Throwable("Unknown error")
                    }
                }
            } catch (e: Exception) {
                NetworkResponseHandler.handleException(e)
            }
        }
    }
}