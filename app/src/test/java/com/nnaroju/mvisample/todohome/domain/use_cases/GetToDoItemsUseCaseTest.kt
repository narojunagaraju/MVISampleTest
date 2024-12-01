package com.nnaroju.mvisample.todohome.domain.use_cases

import com.nnaroju.mvisample.core.domian.model.TodoItem
import com.nnaroju.mvisample.core.domian.repository.TodoRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class GetToDoItemsUseCaseTest {
    @Mock
    lateinit var todoRepository: TodoRepository

    private lateinit var getToDoItemsUseCase: GetToDoItemsUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getToDoItemsUseCase = GetToDoItemsUseCase(todoRepository)
    }

    @Test
    fun `test invoke with todoItem returns long`() = runTest {

        //Arrange
        val items = listOf(
            TodoItem("Title 1", "Description 1"),
            TodoItem("Title 2", "Description 2"),
        )
        Mockito.`when`(todoRepository.getAllTodoItems()).thenReturn(items)

        //Act
        val result = getToDoItemsUseCase.invoke()

        //Assert
        assertEquals(result, items)
    }
}