package com.nnaroju.mvisample.todohome.domain.presentation

import androidx.compose.runtime.Stable
import com.nnaroju.mvisample.core.domian.model.TodoItem
import com.nnaroju.mvisample.utils.UiEffect
import com.nnaroju.mvisample.utils.UiEvent
import com.nnaroju.mvisample.utils.UiState

@Stable
data class TodoHomeScreenState(
    val isLoading: Boolean = false,
    val todoList: List<TodoItem> = emptyList(),
    val errorMessage: String? = null
) : UiState()

sealed class TodoHomeScreenEvent : UiEvent() {
    object FetchAllTodoItems : TodoHomeScreenEvent()
}

sealed class TodoHomeScreenEffect : UiEffect() {
    object NavigateToAddTodoItem : TodoHomeScreenEffect()
}