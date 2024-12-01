package com.nnaroju.mvisample.core.data.mapper

import com.nnaroju.mvisample.core.data.local.TodoEntity
import com.nnaroju.mvisample.core.domian.model.TodoItem

fun TodoEntity.toTodoItem(): TodoItem {
    return TodoItem(
        title = title,
        description = description ?: ""
    )
}

fun TodoItem.toTodoEntity(): TodoEntity {
    return TodoEntity(
        title = title,
        description = description ?: ""
    )
}