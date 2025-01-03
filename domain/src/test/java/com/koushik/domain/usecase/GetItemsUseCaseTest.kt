package com.koushik.domain.usecase

import com.koushik.core.model.Result
import com.koushik.domain.model.Item
import com.koushik.domain.repository.ItemRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetItemsUseCaseTest {

    @MockK
    private lateinit var repository: ItemRepository

    private lateinit var getItemsUseCase: GetItemsUseCase

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        Dispatchers.setMain(testDispatcher)
        getItemsUseCase = GetItemsUseCase(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `invoke should emit loading and success when repository returns items`() = runTest {
        // Arrange
        val mockItems = listOf(Item("1", "Item 1", "", ""), Item("2", "Item 2", "", ""))
        coEvery { repository.fetchItems() } returns kotlin.Result.success(mockItems)

        // Act
        val results = getItemsUseCase().toList()

        // Assert
        assertEquals(Result.Loading, results[0])
        assertEquals(Result.Success(mockItems), results[1])
    }

    @Test
    fun `invoke should emit loading and failure when repository returns an error`() = runTest {
        // Arrange
        val mockException = Exception("Network error")
        coEvery { repository.fetchItems() } returns kotlin.Result.failure(mockException)

        // Act
        val results = getItemsUseCase().toList()

        // Assert
        assertEquals(Result.Loading, results[0])
        assertEquals(Result.Failure(mockException), results[1])
    }

    @Test
    fun `invoke should emit failure when an exception is thrown`() = runTest {
        // Arrange
        val mockException = Exception("Unexpected error")
        coEvery { repository.fetchItems() } throws mockException

        // Act
        val results = getItemsUseCase().toList()

        // Assert
        assertEquals(Result.Loading, results[0])
        assertEquals(Result.Failure(mockException), results[1])
    }
}
