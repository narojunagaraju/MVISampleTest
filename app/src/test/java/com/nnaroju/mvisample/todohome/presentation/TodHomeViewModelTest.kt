package com.nnaroju.mvisample.todohome.presentation

import com.nnaroju.mvisample.MainCoroutineRule
import com.nnaroju.mvisample.core.domian.model.TodoItem
import com.nnaroju.mvisample.todohome.use_cases.GetToDoItemsUseCase
import com.nnaroju.mvisample.todohome.presentation.TodHomeViewModel
import com.nnaroju.mvisample.todohome.presentation.TodoHomeScreenEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class TodoHomeViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var viewModel: TodHomeViewModel

    @Mock
    private lateinit var getToDoItemsUseCase: GetToDoItemsUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = TodHomeViewModel(getToDoItemsUseCase)
    }

    @Test
    fun `sendEvent FetchAllTodoItems should fetch and update state with todo list`() = runTest {
        // Arrange
        val todoItems =
            listOf(TodoItem("Title 1", "Description 1")) // Replace with your actual ToDoItem class
        Mockito.`when`(getToDoItemsUseCase()).thenReturn(todoItems)

        // Act
        viewModel.sendEvent(TodoHomeScreenEvent.FetchAllTodoItems)
        advanceUntilIdle()

        // Assert
        val state = viewModel.state.value
        println(state.todoList)
        assertEquals(false, state.isLoading)
        assertEquals(todoItems, state.todoList)
        assertNull(state.errorMessage)
    }

    @Test
    fun `sendEvent FetchAllTodoItems should handle error and update state`() = runTest {
        // Arrange
        val errorMessage = "Network Error"
        Mockito.`when`(getToDoItemsUseCase()).thenThrow(RuntimeException(errorMessage))

        // Act
        viewModel.sendEvent(TodoHomeScreenEvent.FetchAllTodoItems)
        advanceUntilIdle()

        // Assert
        val state = viewModel.state.value
        assertEquals(false, state.isLoading)
        assertTrue(state.todoList.isEmpty())
        assertEquals(errorMessage, state.errorMessage)
    }

    @Test
    fun `sendEvent SetErrorMessage should update state with error message`() = runTest {
        // Arrange
        val errorMessage = "Some error occurred"
        val event = TodoHomeScreenEvent.SetErrorMessage(errorMessage)

        // Act
        viewModel.sendEvent(event)
        advanceUntilIdle()

        // Assert
        val state = viewModel.state.value
        assertEquals(errorMessage, state.errorMessage)
    }
}
