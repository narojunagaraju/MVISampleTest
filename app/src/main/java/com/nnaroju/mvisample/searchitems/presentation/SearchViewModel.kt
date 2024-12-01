package com.nnaroju.mvisample.searchitems.presentation

import androidx.lifecycle.viewModelScope
import com.nnaroju.mvisample.searchitems.presentation.use_cases.SearchItemsUseCase
import com.nnaroju.mvisample.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchItemsUseCase: SearchItemsUseCase,
    private val dispatcher: CoroutineDispatcher
) : BaseViewModel<SearchScreenState, SearchScreenEffect, SearchScreenEvent>() {

    private var searchJob: Job? = null

    override fun createInitialState(): SearchScreenState = SearchScreenState()

    override fun handleEvent(event: SearchScreenEvent) {
        when (event) {
            is SearchScreenEvent.SearchItems -> searchItems(event)
        }
    }

    private fun searchItems(event: SearchScreenEvent.SearchItems) {
        setState {
            copy(searchQuery = event.searchQuery)
        }
        searchJob?.cancel()
        searchJob = viewModelScope.launch(dispatcher) {
            runCatching {
                delay(2000L)
                if (event.searchQuery.isNotEmpty()) {
                    setState {
                        copy(
                            isLoading = true
                        )
                    }
                    val result = searchItemsUseCase(event.searchQuery.trim())
                    setState {
                        copy(
                            isLoading = false,
                            todoList = result
                        )
                    }
                } else {
                    setState {
                        copy(
                            todoList = emptyList(),
                            isLoading = false
                        )
                    }
                }
            }.onFailure {
                setState {
                    copy(
                        isLoading = false,
                        errorMessage = it.message,
                        todoList = emptyList()
                    )
                }
            }
        }
    }
}
