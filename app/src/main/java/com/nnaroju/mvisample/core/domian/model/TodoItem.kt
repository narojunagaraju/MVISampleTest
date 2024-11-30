package com.nnaroju.mvisample.core.domian.model

data class TodoItem(
    var id: Int? = 0,
    var title: String,
    var description: String? = null
)