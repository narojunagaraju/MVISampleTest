package com.nnaroju.mvisample.todohome.domain.presentation

import androidx.lifecycle.viewModelScope
import com.nnaroju.mvisample.todohome.domain.use_cases.GetToDoItemsUseCase
import com.nnaroju.mvisample.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodHomeViewModel @Inject constructor(
    private val getToDoItemsUseCase: GetToDoItemsUseCase
) : BaseViewModel<TodoHomeScreenState, TodoHomeScreenEffect, TodoHomeScreenEvent>() {

    init {
        sendEvent(TodoHomeScreenEvent.FetchAllTodoItems)
    }

    override fun createInitialState(): TodoHomeScreenState = TodoHomeScreenState()

    override fun handleEvent(event: TodoHomeScreenEvent) {
        when (event) {
            TodoHomeScreenEvent.FetchAllTodoItems -> fetchAllTodoItems()
            is TodoHomeScreenEvent.SetErrorMessage -> updateErrorMessage(event)
        }
    }

    private fun updateErrorMessage(event: TodoHomeScreenEvent.SetErrorMessage) {
        setState {
            copy(errorMessage = event.errorMessage)
        }
    }

    private fun fetchAllTodoItems() = viewModelScope.launch {
        runCatching {
            setState {
                copy(
                    isLoading = true,
                    errorMessage = null
                )
            }
            val todoItems = getToDoItemsUseCase()
            setState {
                copy(
                    isLoading = false,
                    todoList = todoItems
                )
            }
        }.onFailure {
            setState {
                copy(
                    isLoading = false,
                    errorMessage = it.message
                )
            }
        }
    }
}