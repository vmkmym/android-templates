package com.example.composeproject.model

import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class TodoRepository(private val todoDao: TodoDao) {

    suspend fun addTodo(todoItem: TodoItem) {
        todoDao.addTodo(todoItem)
    }

    fun getTodos(): Flow<List<TodoItem>> = todoDao.getAllTodos()

    suspend fun updateTodo(todoItem: TodoItem) {
        todoDao.updateTodo(todoItem)
    }

    suspend fun deleteTodo(todoItem: TodoItem) {
        todoDao.deleteTodo(todoItem)
    }
}