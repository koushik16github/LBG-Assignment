package com.koushik.lbgassignment.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.koushik.data.model.Item
import com.koushik.domain.model.Result
import com.koushik.domain.usecase.GetItemsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel
    private val getItemsUseCase: GetItemsUseCase = mockk()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MainViewModel(getItemsUseCase)
    }

    @Test
    fun `fetchItems should update items when use case returns success`() = runTest {
        // Arrange
        val mockItems = listOf(Item("1", "Item 1", "Description", "URL"), Item("2", "Item 2", "Description", "URL"))
        coEvery { getItemsUseCase.invoke() } returns flowOf(Result.Success(mockItems)) // Mocking the invoke method

        // Act
        viewModel.fetchItems()
        advanceUntilIdle()

        // Assert
        assertTrue(viewModel.items.value is Result.Success)
        assertEquals(mockItems, (viewModel.items.value as Result.Success).data)
    }

    @Test
    fun `fetchItems should update items with failure when use case throws an error`() = runTest {
        // Arrange
        val mockException = Exception("Network error")
        coEvery { getItemsUseCase.invoke() } returns flowOf(Result.Failure(mockException)) // Mocking the invoke method

        // Act
        viewModel.fetchItems()
        advanceUntilIdle()

        // Assert
        assertTrue(viewModel.items.value is Result.Failure)
        assertEquals(mockException, (viewModel.items.value as Result.Failure).exception)
    }

    @Test
    fun `getItemById should return the correct item when exists`() = runTest {
        // Arrange
        val mockItems = listOf(Item("1", "Item 1", "Description", "URL"), Item("2", "Item 2", "Description", "URL"))
        coEvery { getItemsUseCase.invoke() } returns flowOf(Result.Success(mockItems)) // Mocking the invoke method

        // Act
        viewModel.fetchItems()
        val item = viewModel.getItemById("1")

        // Assert
        assertEquals(mockItems[0], item)
    }

    @Test
    fun `getItemById should return null when item does not exist`() {
        // Arrange
        val mockItems = listOf(Item("1", "Item 1", "Description", "URL"), Item("2", "Item 2", "Description", "URL"))
        coEvery { getItemsUseCase.invoke() } returns flowOf(Result.Success(mockItems)) // Mocking the invoke method

        // Act
        viewModel.fetchItems()
        val item = viewModel.getItemById("3")

        // Assert
        assertEquals(null, item)
    }
}