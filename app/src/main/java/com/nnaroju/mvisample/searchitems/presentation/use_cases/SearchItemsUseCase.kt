package com.nnaroju.mvisample.searchitems.presentation.use_cases

import com.nnaroju.mvisample.core.domian.model.TodoItem
import com.nnaroju.mvisample.core.domian.repository.TodoRepository

class SearchItemsUseCase(
    private val todoRepository: TodoRepository
) {
    suspend operator fun invoke(searchQuery: String): List<TodoItem> {
        return todoRepository.searchItems(searchQuery)
    }
}


