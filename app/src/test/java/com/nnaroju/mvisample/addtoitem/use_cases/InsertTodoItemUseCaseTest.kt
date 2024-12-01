package com.nnaroju.mvisample.addtoitem.use_cases

import com.nnaroju.mvisample.core.domian.model.TodoItem
import com.nnaroju.mvisample.core.domian.repository.TodoRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any

class InsertTodoItemUseCaseTest {
    @Mock
    lateinit var todoRepository: TodoRepository

    private lateinit var insertTodoItemUseCase: InsertTodoItemUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        insertTodoItemUseCase = InsertTodoItemUseCase(todoRepository)
    }

    @Test
    fun `test invoke with todoItem returns long`() = runTest {
        //Arrange
        Mockito.`when`(todoRepository.upsertTodoItem(any())).thenReturn(1L)

        //Act
        val result = insertTodoItemUseCase.invoke(TodoItem("Title", "Description"))

        //Assert
        assertTrue(result == 1L)
    }
}