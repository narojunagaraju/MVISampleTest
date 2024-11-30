package com.nnaroju.mvisample.core.data.mapper

import com.nnaroju.mvisample.core.data.local.TodoEntity
import com.nnaroju.mvisample.core.domian.model.TodoItem

fun TodoEntity.toTodoItem(): TodoItem {
    return TodoItem(
        id = id ?: 0,
        title = title,
        description = description ?: ""
    )
}

fun TodoItem.toTodoEntity(): TodoEntity {
    return TodoEntity(
        id = id,
        title = title,
        description = description ?: ""
    )
}