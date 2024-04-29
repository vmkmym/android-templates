package com.example.composeproject.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    // https://developer.android.com/training/data-storage/room/async-queries?hl=ko#flow-coroutines
    // Loads all todos from the todo_table
    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllTodos(): Flow<List<TodoItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTodo(todoItem: TodoItem)

    @Update
    suspend fun updateTodo(todoItem: TodoItem)

    @Delete
    suspend fun deleteTodo(todoItem: TodoItem)

    @Query("Select * from todo_table where id=:id")
    fun getATodoById(id:Long): Flow<TodoItem>

    @Query("DELETE FROM todo_table WHERE 'is-completed' = 1")
    suspend fun deleteCompletedTodos()

    @Query("SELECT * FROM todo_table ORDER BY date")
    fun getTodosSortedByDate(): Flow<List<TodoItem>>

    @Query("SELECT * FROM todo_table ORDER BY dueDate DESC")
    fun getTodosSortedByDueDateDescending(): Flow<List<TodoItem>>

    @Query("SELECT * FROM todo_table ORDER BY dueDate ASC")
    fun getTodosSortedByDueDateAscending(): Flow<List<TodoItem>>

    @Query("SELECT * FROM todo_table ORDER BY priority DESC")
    fun getTodosSortedByPriorityDescending(): Flow<List<TodoItem>>

    @Query("SELECT * FROM todo_table ORDER BY priority ASC")
    fun getTodosSortedByPriorityAscending(): Flow<List<TodoItem>>
}