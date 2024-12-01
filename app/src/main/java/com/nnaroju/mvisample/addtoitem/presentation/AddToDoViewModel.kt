package com.nnaroju.mvisample.addtoitem.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.nnaroju.mvisample.addtoitem.use_cases.InsertTodoItemUseCase
import com.nnaroju.mvisample.core.domian.model.TodoItem
import com.nnaroju.mvisample.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddToDoViewModel @Inject constructor(
    private val insertTodoItemUseCase: InsertTodoItemUseCase
) : BaseViewModel<AddToDoScreenState, AddToDoScreenEffect, AddToDoScreenEvent>() {
    override fun createInitialState(): AddToDoScreenState = AddToDoScreenState()

    override fun handleEvent(event: AddToDoScreenEvent) {
        when (event) {
            AddToDoScreenEvent.AddToDoItem -> addItem()
            is AddToDoScreenEvent.OnTitleChanged -> onTitleChange(event)
            is AddToDoScreenEvent.OnDescriptionChanged -> onDescriptionChanged(event)
        }
    }

    private fun addItem() {
        if (currentState.titleState.title.isNotEmpty()) {
            viewModelScope.launch {
                setState { copy(isLoading = true) }
                runCatching {
                    val result = insertTodoItemUseCase(
                        TodoItem(
                            title = currentState.titleState.title,
                            description = currentState.description
                        )
                    )
                    if (result > 0L) {
                        delay(3000L)
                        setState { copy(isLoading = false) }
                        sendEffect(AddToDoScreenEffect.NavigateToHome)
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
        } else {
            setState {
                copy(titleState = AddToDoScreenState.TitleState(errorMessage = EMPTY_TITLE))
            }
        }
    }

    private fun onDescriptionChanged(event: AddToDoScreenEvent.OnDescriptionChanged) {
        setState {
            copy(
                description = event.description
            )
        }
    }

    private fun onTitleChange(event: AddToDoScreenEvent.OnTitleChanged) {
        setState {
            copy(
                titleState = AddToDoScreenState.TitleState(
                    title = event.title,
                    errorMessage = if (event.title.isEmpty()) {
                        EMPTY_TITLE
                    } else {
                        null
                    }
                )
            )
        }
    }

    companion object {
        const val EMPTY_TITLE = "Title Can't be empty"
    }

}