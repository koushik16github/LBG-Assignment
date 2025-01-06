package com.koushik.data.model

import com.google.gson.annotations.SerializedName

data class ItemDto(
    @SerializedName("article_id") val articleId: String,
    val title: String,
    val description: String?,
    @SerializedName("image_url") val imageUrl: String?
)