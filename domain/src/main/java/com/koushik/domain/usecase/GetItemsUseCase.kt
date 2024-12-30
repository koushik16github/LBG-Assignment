package com.koushik.domain.usecase

import com.koushik.data.model.Item
import com.koushik.data.repository.NetworkRepository
import com.koushik.domain.model.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetItemsUseCase @Inject constructor(
    private val repository: NetworkRepository
) {
    operator fun invoke(): Flow<Result<List<Item>>> = flow {
        try {
            emit(Result.Loading)
            val result = repository.fetchItems()

            result.fold(
                onSuccess = { items ->
                    emit(Result.Success(items))
                },
                onFailure = { throwable ->
                    emit(Result.Failure(throwable))
                }
            )
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}