package com.koushik.domain.usecase

import com.koushik.data.model.Item
import com.koushik.data.repository.NetworkRepository
import com.koushik.domain.model.Result
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetItemsUseCaseTest {

    private lateinit var getItemsUseCase: GetItemsUseCase
    private val mockRepository: NetworkRepository = mockk()

    @Before
    fun setup() {
        getItemsUseCase = GetItemsUseCase(mockRepository)
    }

    @Test
    fun `invoke should emit loading and success when repository returns items`() = runTest {
        // Arrange
        val mockItems = listOf(Item("1", "Item 1", "", ""), Item("2", "Item 2", "", ""))
        coEvery { mockRepository.fetchItems() } returns kotlin.Result.success(mockItems)

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
        coEvery { mockRepository.fetchItems() } returns kotlin.Result.failure(mockException)

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
        coEvery { mockRepository.fetchItems() } throws mockException

        // Act
        val results = getItemsUseCase().toList()

        // Assert
        assertEquals(Result.Loading, results[0])
        assertEquals(Result.Failure(mockException), results[1])
    }
}
