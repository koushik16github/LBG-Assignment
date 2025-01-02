package com.koushik.lbgassignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koushik.data.model.Item
import com.koushik.core.model.Result
import com.koushik.domain.usecase.GetItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getItemsUseCase: GetItemsUseCase
) : ViewModel() {

    private val _items = MutableStateFlow<Result<List<Item>>>(Result.Loading)
    val items: StateFlow<Result<List<Item>>> = _items

    init {
        fetchItems()
    }

    fun fetchItems() {
        viewModelScope.launch {
            getItemsUseCase()
                .catch { e -> _items.value = Result.Failure(e as Exception) }
                .collect { result -> _items.value = result }
        }
    }

    fun getItemById(itemId: String?): Item? {
        val currentItems = (_items.value as? Result.Success)?.data
        return currentItems?.find { it.articleId == itemId }
    }
}