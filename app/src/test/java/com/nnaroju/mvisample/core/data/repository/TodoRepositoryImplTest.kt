package com.nnaroju.mvisample.core.data.repository

import com.nnaroju.mvisample.core.data.local.TodoDao
import com.nnaroju.mvisample.core.data.local.TodoEntity
import com.nnaroju.mvisample.core.data.mapper.toTodoItem
import com.nnaroju.mvisample.core.domian.model.TodoItem
import com.nnaroju.mvisample.core.domian.repository.TodoRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.verify

class TodoRepositoryImplTest {

    @Mock
    lateinit var todoDao: TodoDao

    private lateinit var todoRepository: TodoRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        todoRepository = TodoRepositoryImpl(todoDao)
    }

    @Test
    fun `test upsertToItem invokes the upsert on dao`() = runTest {
        //Act
        todoRepository.upsertTodoItem(TodoItem("title", "Description"))

        //Assert
        verify(todoDao).upsertTodItem(any())
    }

    @Test
    fun `test deleteTodoItem invokes the deleteTodoItem on dao`() = runTest {
        //Act
        todoRepository.deleteTodoItem(TodoItem("title", "Description"))

        //Assert
        verify(todoDao).deleteTodoItems(any())
    }

    @Test
    fun `test getAllTodoItems invokes the getTodoItems on dao`() = runTest {
        //Arrange
        val items = listOf(TodoEntity(id = 0, title = "title", description = "Description"))
        Mockito.`when`(todoDao.getTodoItems()).thenReturn(items)
        //Act
        val result = todoRepository.getAllTodoItems()

        //Assert
        verify(todoDao).getTodoItems()
        assertEquals(items.map { it.toTodoItem() }, result)

    }
}