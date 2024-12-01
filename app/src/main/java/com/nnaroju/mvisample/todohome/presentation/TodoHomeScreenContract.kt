package com.nnaroju.mvisample.todohome.presentation

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
    data object FetchAllTodoItems : TodoHomeScreenEvent()
    data class SetErrorMessage(val errorMessage: String = "") : TodoHomeScreenEvent()
}

sealed class TodoHomeScreenEffect : UiEffect() {
    data object NavigateToAddTodoItem : TodoHomeScreenEffect()
    data object NavigateToSearchItem : TodoHomeScreenEffect()
}