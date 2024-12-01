package com.nnaroju.mvisample.core.data.local

import androidx.room.Dao
import androidx.room.Delete
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

    @Query("""
    SELECT * FROM todoentity 
    WHERE LOWER(title) LIKE '%' || LOWER(:searchQuery) || '%' 
       OR LOWER(description) LIKE '%' || LOWER(:searchQuery) || '%'
""")
    fun searchTodoItems(searchQuery: String): List<TodoEntity>
}