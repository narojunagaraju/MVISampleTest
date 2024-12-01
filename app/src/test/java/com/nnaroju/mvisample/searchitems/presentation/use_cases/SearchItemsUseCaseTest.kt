package com.nnaroju.mvisample.searchitems.presentation.use_cases

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

class SearchItemsUseCaseTest {

    @Mock
    lateinit var todoRepository: TodoRepository

    private lateinit var searchItemsUseCase: SearchItemsUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        searchItemsUseCase = SearchItemsUseCase(todoRepository)
    }

    @Test
    fun `test invoke with searchItems returns matching list`() = runTest {
        //Arrange
        val items = listOf(TodoItem("title", "description"))
        Mockito.`when`(todoRepository.searchItems(any())).thenReturn(items)

        //Act
        val result = searchItemsUseCase.invoke("title")

        //Assert
        assertEquals(items, result)
    }
}