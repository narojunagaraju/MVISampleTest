package com.nnaroju.mvisample.addtoitem.presentation

import com.nnaroju.mvisample.MainCoroutineRule
import com.nnaroju.mvisample.addtoitem.use_cases.InsertTodoItemUseCase
import com.nnaroju.mvisample.core.domian.model.TodoItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any

@ExperimentalCoroutinesApi
class AddToDoViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var viewModel: AddToDoViewModel

    @Mock
    private lateinit var insertTodoItemUseCase: InsertTodoItemUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = AddToDoViewModel(insertTodoItemUseCase)
    }

    @Test
    fun `sendEvent OnTitleChanged should update title in state`() = runTest {
        // Arrange
        val newTitle = "New Task"

        // Act
        viewModel.sendEvent(AddToDoScreenEvent.OnTitleChanged(newTitle))
        advanceUntilIdle()

        // Assert
        val state = viewModel.state.value
        assertEquals(newTitle, state.titleState.title)
        assertNull(state.titleState.errorMessage)
    }

    @Test
    fun `sendEvent OnTitleChanged with empty title should set error message`() = runTest {
        // Arrange
        val emptyTitle = ""

        // Act
        viewModel.sendEvent(AddToDoScreenEvent.OnTitleChanged(emptyTitle))
        advanceUntilIdle()

        // Assert
        val state = viewModel.state.value
        assertEquals(emptyTitle, state.titleState.title)
        assertEquals(AddToDoViewModel.EMPTY_TITLE, state.titleState.errorMessage)
    }

    @Test
    fun `sendEvent OnDescriptionChanged should update description in state`() = runTest {
        // Arrange
        val newDescription = "This is a description."

        // Act
        viewModel.sendEvent(AddToDoScreenEvent.OnDescriptionChanged(newDescription))
        advanceUntilIdle()

        // Assert
        val state = viewModel.state.value
        assertEquals(newDescription, state.description)
    }

    @Test
    fun `sendEvent AddToDoItem should call insertTodoItemUseCase`() =
        runTest {
            // Arrange
            val title = "Task Title"
            val description = "Task Description"
            val todoItem = TodoItem(title = title, description = description)
            Mockito.`when`(insertTodoItemUseCase(todoItem)).thenReturn(1L)

            viewModel.sendEvent(AddToDoScreenEvent.OnTitleChanged(title))
            viewModel.sendEvent(AddToDoScreenEvent.OnDescriptionChanged(description))

            // Act
            viewModel.sendEvent(AddToDoScreenEvent.AddToDoItem)
            advanceUntilIdle() // Wait for coroutines to complete

            // Assert
            val state = viewModel.state.value
            assertEquals(false, state.isLoading)
            assertEquals(null, state.errorMessage)
            assertEquals(AddToDoScreenEffect.NavigateToHome, viewModel.effect.first())
        }

    @Test
    fun `sendEvent AddToDoItem should update state with error message on failure`() = runTest {
        // Arrange
        val title = "Task Title"
        val description = "Task Description"
        val errorMessage = "Database error"
        Mockito.`when`(insertTodoItemUseCase(any())).thenThrow(RuntimeException(errorMessage))

        viewModel.sendEvent(AddToDoScreenEvent.OnTitleChanged(title))
        viewModel.sendEvent(AddToDoScreenEvent.OnDescriptionChanged(description))

        // Act
        viewModel.sendEvent(AddToDoScreenEvent.AddToDoItem)
        advanceUntilIdle() // Wait for coroutines to complete

        // Assert
        val state = viewModel.state.value
        assertEquals(false, state.isLoading)
        assertEquals(errorMessage, state.errorMessage)
    }

    @Test
    fun `sendEvent AddToDoItem with empty title should set error message in state`() = runTest {
        // Arrange
        val emptyTitle = ""

        // Act
        viewModel.sendEvent(AddToDoScreenEvent.OnTitleChanged(emptyTitle))
        viewModel.sendEvent(AddToDoScreenEvent.AddToDoItem)
        advanceUntilIdle()

        // Assert
        val state = viewModel.state.value
        assertEquals(AddToDoViewModel.EMPTY_TITLE, state.titleState.errorMessage)
    }
}
