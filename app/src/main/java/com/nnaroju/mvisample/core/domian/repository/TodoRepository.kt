package com.nnaroju.mvisample.core.domian.repository

import com.nnaroju.mvisample.core.domian.model.TodoItem

interface TodoRepository {
    suspend fun upsertTodoItem(todoItem: TodoItem): Long

    suspend fun deleteTodoItem(todoItem: TodoItem)

    suspend fun getAllTodoItems(): List<TodoItem>

    suspend fun searchItems(searchQuery: String): List<TodoItem>
}