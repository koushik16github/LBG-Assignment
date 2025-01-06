package com.koushik.data.mapper

import com.koushik.data.model.ItemDto
import com.koushik.domain.model.Item

fun ItemDto.toDomainModel(): Item {
    return Item(
        articleId = this.articleId,
        title = this.title,
        description = this.description,
        imageUrl = this.imageUrl
    )
}

fun Item.toDataModel(): ItemDto {
    return ItemDto(
        articleId = this.articleId,
        title = this.title,
        description = this.description,
        imageUrl = this.imageUrl
    )
}