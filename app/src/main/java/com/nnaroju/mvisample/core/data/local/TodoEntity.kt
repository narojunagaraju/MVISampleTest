package com.nnaroju.mvisample.core.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var title: String,
    var description: String? = null,
)