package com.nnaroju.mvisample.addtoitem.presentation

import androidx.compose.runtime.Stable
import com.nnaroju.mvisample.utils.UiEffect
import com.nnaroju.mvisample.utils.UiEvent
import com.nnaroju.mvisample.utils.UiState

@Stable
data class AddToDoScreenState(
    val isLoading: Boolean = false,
    val titleState: TitleState = TitleState(),
    val description: String = "",
    val errorMessage: String? = null
) : UiState() {

    data class TitleState(
        val title: String = "",
        val errorMessage: String? = null
    )
}

sealed class AddToDoScreenEvent : UiEvent() {
    data object AddToDoItem : AddToDoScreenEvent()
    data class OnTitleChanged(val title: String) : AddToDoScreenEvent()
    data class OnDescriptionChanged(val description: String) : AddToDoScreenEvent()
}

sealed class AddToDoScreenEffect : UiEffect() {
    data class NavigateBack(val errorMessage: String = "") : AddToDoScreenEffect()
    data object NavigateToHome : AddToDoScreenEffect()
}