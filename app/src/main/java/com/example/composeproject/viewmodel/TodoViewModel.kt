package com.example.composeproject.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.composeproject.model.Graph
import com.example.composeproject.model.TodoItem
import com.example.composeproject.model.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.Date

class TodoViewModel(private val todoRepository: TodoRepository = Graph.todoRepository) :
    ViewModel() {
    var selectedTodo: TodoItem? = null
    var todoTitleState by mutableStateOf("")
    var todoDescriptionState by mutableStateOf("")
    var dueDateState by mutableStateOf<Date?>(null)
    private var selectedTodosState by mutableStateOf<List<TodoItem>>(emptyList())


    fun onTodoTitleChanged(newTitle: String) {
        todoTitleState = newTitle
    }

    fun onTodoDescriptionChanged(newDescription: String) {
        todoDescriptionState = newDescription
    }

    lateinit var getTodos: Flow<List<TodoItem>>

    init {
        viewModelScope.launch {
            getTodos = todoRepository.getTodos()
        }
    }

    private fun addTodo(todo: TodoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            todoRepository.addTodo(todo)
            onTodoTitleChanged("")
            onTodoDescriptionChanged("")
        }
    }

    fun updateTodo() {
        selectedTodo?.let { selectedTodo ->
            val updatedTodo = TodoItem(
                id = selectedTodo.id,
                title = todoTitleState,
                description = todoDescriptionState,
                date = Date(),
                dueDate = dueDateState,
                isCompleted = false
            )
            viewModelScope.launch(Dispatchers.IO) {
                todoRepository.updateTodo(updatedTodo)
                getTodos = todoRepository.getTodos()
            }
        }
    }

    fun onTodoSelected(todo: TodoItem) {
        selectedTodosState = selectedTodosState + todo
    }

    fun onTodoDeselected(todo: TodoItem) {
        selectedTodosState = selectedTodosState - todo
    }

    fun deleteSelectedTodos() {
        selectedTodosState.forEach { todo ->
            viewModelScope.launch(Dispatchers.IO) {
                todoRepository.deleteTodo(todo)
            }
        }
        selectedTodosState = emptyList()
        viewModelScope.launch {
            getTodos = todoRepository.getTodos()
        }
    }

    fun addCurrentTodo() {
        val todo = TodoItem(
            title = todoTitleState,
            description = todoDescriptionState,
            date = Date(),
            dueDate = dueDateState,
            isCompleted = false
        )
        addTodo(todo)
    }
}

class TodoViewModelFactory(private val repository: TodoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TodoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}