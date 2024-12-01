package com.nnaroju.mvisample.core.data.repository

import com.nnaroju.mvisample.core.data.local.TodoDatabase
import com.nnaroju.mvisample.core.data.mapper.toTodoEntity
import com.nnaroju.mvisample.core.data.mapper.toTodoItem
import com.nnaroju.mvisample.core.domian.model.TodoItem
import com.nnaroju.mvisample.core.domian.repository.TodoRepository

class TodoRepositoryImpl(
    todoDatabase: TodoDatabase
) : TodoRepository {

    private val todoDao = todoDatabase.todoDao

    override suspend fun upsertTodoItem(todoItem: TodoItem): Long {
        return todoDao.upsertTodItem(todoItem.toTodoEntity())
    }

    override suspend fun deleteTodoItem(todoItem: TodoItem) {
        todoDao.deleteTodoItems(todoItem.toTodoEntity())
    }

    override suspend fun getAllTodoItems(): List<TodoItem> {
        return todoDao.getTodoItems().map { it.toTodoItem() }
    }
}