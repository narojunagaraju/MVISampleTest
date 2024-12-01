package com.nnaroju.mvisample.searchitems.presentation

import com.nnaroju.mvisample.MainCoroutineRule
import com.nnaroju.mvisample.core.domian.model.TodoItem
import com.nnaroju.mvisample.searchitems.presentation.use_cases.SearchItemsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class SearchViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var viewModel: SearchViewModel

    @Mock
    private lateinit var searchItemsUseCase: SearchItemsUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = SearchViewModel(searchItemsUseCase, coroutineRule.dispatcher)
    }

    @Test
    fun `test search query update`() = runTest {
        // Arrange
        val query = "test query"

        // Act
        viewModel.sendEvent(SearchScreenEvent.SearchItems(query))
        advanceUntilIdle()

        // Assert
        val state = viewModel.state.value
        assertEquals(query, state.searchQuery)
    }

    @Test
    fun `test debouncing search items`() = runTest {
        // Arrange
        val query = "test query"
        val result = listOf(TodoItem(title = "title", description = "description"))
        whenever(searchItemsUseCase(any())).thenReturn(result)

        // Act
        viewModel.sendEvent(SearchScreenEvent.SearchItems(query))
        advanceUntilIdle()

        //Assert
        val state = viewModel.state.value
        assertEquals(result, state.todoList)
        assertFalse(state.isLoading)
    }

    @Test
    fun `test search success updates state`() = runTest {
        // Arrange
        val query = "test query"
        val result = listOf(TodoItem(title = "title", description = "description"))
        whenever(searchItemsUseCase(query)).thenReturn(result)

        // Act
        viewModel.sendEvent(SearchScreenEvent.SearchItems(query))
        advanceUntilIdle()

        //Assert
        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertNull(state.errorMessage)
    }

    @Test
    fun `test search failure updates state`() = runTest {
        // Arrange
        val query = "test query"
        val errorMessage = "Error occurred"
        whenever(searchItemsUseCase(query)).thenThrow(RuntimeException(errorMessage))

        // Act
        viewModel.sendEvent(SearchScreenEvent.SearchItems(query))
        advanceUntilIdle()

        //Assert
        val state = viewModel.state.value
        assertTrue(state.errorMessage?.contains(errorMessage) == true)
        assertFalse(state.isLoading)
        assertTrue(state.todoList.isEmpty())
    }
}
