package com.koushik.lbgassignment.viewmodel

import com.koushik.data.model.Item
import com.koushik.domain.model.Result
import com.koushik.domain.usecase.GetItemsUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @MockK
    private lateinit var getItemsUseCase: GetItemsUseCase

    private lateinit var viewModel: MainViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        Dispatchers.setMain(testDispatcher)
        viewModel = MainViewModel(getItemsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchItems should update items when use case returns success`() = runTest {
        // Arrange
        val mockItems = listOf(
            Item("1", "Item 1", "Description", "URL"),
            Item("2", "Item 2", "Description", "URL")
        )
        coEvery { getItemsUseCase.invoke() } returns flowOf(Result.Success(mockItems))

        // Act
        viewModel.fetchItems()
        advanceUntilIdle()

        // Assert
        val result = viewModel.items.value
        assert(result is Result.Success && result.data == mockItems)
    }

    @Test
    fun `fetchItems should update items when use case returns failure`() = runTest {
        // Arrange
        val mockException = Exception("Test exception")
        coEvery { getItemsUseCase.invoke() } returns flowOf(Result.Failure(mockException))

        // Act
        viewModel.fetchItems()
        advanceUntilIdle()

        // Assert
        val result = viewModel.items.value
        assert(result is Result.Failure && result.exception == mockException)
    }

    @Test
    fun `items should initially be in loading state`() {
        // Assert
        val initialState = viewModel.items.value
        assert(initialState is Result.Loading)
    }

    @Test
    fun `getItemById should return the correct item when exists`() = runTest {
        // Arrange
        val mockItems = listOf(
            Item("1", "Item 1", "Description", "URL"),
            Item("2", "Item 2", "Description", "URL")
        )
        coEvery { getItemsUseCase.invoke() } returns flowOf(Result.Success(mockItems))

        // Act
        viewModel.fetchItems()
        advanceUntilIdle()
        val item = viewModel.getItemById("1")

        // Assert
        assertEquals(mockItems[0], item)
    }

    @Test
    fun `getItemById should return null when item does not exist`() {
        // Arrange
        val mockItems = listOf(
            Item("1", "Item 1", "Description", "URL"),
            Item("2", "Item 2", "Description", "URL")
        )
        coEvery { getItemsUseCase.invoke() } returns flowOf(Result.Success(mockItems))

        // Act
        viewModel.fetchItems()
        val item = viewModel.getItemById("3")

        // Assert
        assertEquals(null, item)
    }
}