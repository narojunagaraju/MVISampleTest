package com.nnaroju.mvisample.todohome.use_cases

import com.nnaroju.mvisample.core.domian.model.TodoItem
import com.nnaroju.mvisample.core.domian.repository.TodoRepository

class GetToDoItemsUseCase(
    private val todoRepository: TodoRepository
) {
    suspend operator fun invoke(): List<TodoItem> {
        return todoRepository.getAllTodoItems()
    }
}