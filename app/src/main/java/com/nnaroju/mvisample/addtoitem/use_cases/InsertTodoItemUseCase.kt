package com.nnaroju.mvisample.addtoitem.use_cases

import com.nnaroju.mvisample.core.domian.model.TodoItem
import com.nnaroju.mvisample.core.domian.repository.TodoRepository

class InsertTodoItemUseCase(private val todoRepository: TodoRepository) {
    suspend operator fun invoke(todoItem: TodoItem): Long {
        return todoRepository.upsertTodoItem(todoItem)
    }
}
