package com.example.composeproject.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeproject.model.TodoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel : ViewModel() {
    // 라디오 버튼
    var isCompleted by mutableStateOf(false)
    fun updateIsCompleted(todoItem: TodoItem) {
        todoItem.isCompleted = !todoItem.isCompleted
    }

    // drawer
    var title by mutableStateOf("")
    fun updateTitle(newTitle: String) {
        title = newTitle
    }

    var description by mutableStateOf("")
    fun updateDescription(newDescription: String) {
        description = newDescription
    }

    // todolist에 title, description을 추가하는 로직
    private val _todoItems = mutableStateOf(listOf<TodoItem>())
    val todoItems = _todoItems

    fun addTodoItem() {
        viewModelScope.launch(Dispatchers.IO) {
            val newTodoItem = TodoItem(title = title, description = description)
            _todoItems.value = _todoItems.value.plus(newTodoItem)
            title = ""
            description = ""
        }
    }
}