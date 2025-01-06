package com.koushik.data.util

import retrofit2.Response

object NetworkResponseHandler {

    fun <T, R> handleResponse(
        response: Response<T>,
        onSuccess: (T) -> R
    ): Result<R> {
        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                try {
                    Result.success(onSuccess(body))
                } catch (e: Exception) {
                    Result.failure(Throwable("Unexpected response format"))
                }
            } else {
                Result.failure(Throwable("Response body is null"))
            }
        } else {
            Result.failure(Throwable(response.message()))
        }
    }

    fun handleException(exception: Exception): Result<Nothing> {
        return Result.failure(exception)
    }
}