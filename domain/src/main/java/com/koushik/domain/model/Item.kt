package com.koushik.domain.model

data class Item(
    val articleId: String,
    val title: String,
    val description: String?,
    val imageUrl: String?
)