package com.nnaroju.mvisample.searchitems.presentation

import androidx.compose.runtime.Stable
import com.nnaroju.mvisample.core.domian.model.TodoItem
import com.nnaroju.mvisample.utils.UiEffect
import com.nnaroju.mvisample.utils.UiEvent
import com.nnaroju.mvisample.utils.UiState

@Stable
data class SearchScreenState(
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val todoList: List<TodoItem> = emptyList(),
    val errorMessage: String? = null
) : UiState()


sealed class SearchScreenEvent : UiEvent() {
    data class SearchItems(val searchQuery: String) : SearchScreenEvent()
}

sealed class SearchScreenEffect : UiEffect() {
    data object NavigateToHome : SearchScreenEffect()
}