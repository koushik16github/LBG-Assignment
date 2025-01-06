package com.koushik.domain.repository

import com.koushik.domain.model.Item

interface ItemRepository {
    suspend fun fetchItems(): Result<List<Item>>
}