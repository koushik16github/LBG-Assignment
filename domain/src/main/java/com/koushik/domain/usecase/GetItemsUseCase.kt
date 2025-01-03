package com.koushik.domain.usecase

import com.koushik.core.model.Result
import com.koushik.domain.model.Item
import com.koushik.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetItemsUseCase @Inject constructor(
    private val repository: ItemRepository
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