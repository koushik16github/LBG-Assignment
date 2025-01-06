package com.koushik.data.api

data class BaseResponse<T>(
    val status: String,
    val results: T?
)