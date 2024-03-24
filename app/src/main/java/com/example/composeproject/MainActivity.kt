package com.example.composeproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.composeproject.model.TodoRepository
import com.example.composeproject.model.TodoRoomDatabase
import com.example.composeproject.ui.theme.ComposeProjectTheme
import com.example.composeproject.view.TodoListScreen
import com.example.composeproject.viewmodel.TodoViewModel
import com.example.composeproject.viewmodel.TodoViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeProjectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val database = TodoRoomDatabase.getDatabase(this)
                    val todoDao = database.todoDao()
                    val repository = TodoRepository(todoDao)
                    val viewModelFactory = TodoViewModelFactory(repository)
                    val viewModel = ViewModelProvider(
                        this,
                        viewModelFactory
                    )[TodoViewModel::class.java]
                    TodoListScreen(viewModel = viewModel)
                }
            }
        }
    }
}
