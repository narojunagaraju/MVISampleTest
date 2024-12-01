package com.nnaroju.mvisample.core.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface TodoDao {
    @Upsert
    suspend fun upsertTodItem(todoEntity: TodoEntity): Long

    @Query("SELECT * FROM todoentity")
    suspend fun getTodoItems(): List<TodoEntity>

    @Delete
    suspend fun deleteTodoItems(todoEntity: TodoEntity)
}