@file:Suppress("DEPRECATION")

package com.example.composeproject.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeproject.R
import com.example.composeproject.model.TodoItem
import com.example.composeproject.viewmodel.TodoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
@ExperimentalMaterial3Api
fun TodoListScreen(viewModel: TodoViewModel) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopBar() },
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = {
                    showBottomSheet = true
                }
            ) {
                Icon(Icons.Filled.Create, contentDescription = "Add Todo")
            }
        }
    ) {
        val todolist = viewModel.getTodos.collectAsState(initial = listOf())
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            items(todolist.value) { todoItem ->
                if (showBottomSheet) {
                    ModalSheet(sheetState, viewModel, scope) {
                        showBottomSheet = false
                    }
                }
                TodoListCard(todoItem, viewModel)
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun ModalSheet(
    sheetState: SheetState,
    viewModel: TodoViewModel,
    scope: CoroutineScope,
    onDismissRequest: () -> Unit
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            onDismissRequest()
            scope.launch { sheetState.hide() }
        },
        content = {
            Column {
                TextField(
                    value = viewModel.todoTitleState,
                    onValueChange = viewModel::onTodoTitleChanged,
                    maxLines = 1,
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.jetbrains))
                    ),
                    colors = transparentTextFieldColors(),
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                )
                TextField(
                    value = viewModel.todoDescriptionState,
                    onValueChange = viewModel::onTodoDescriptionChanged,
                    maxLines = Int.MAX_VALUE,
                    textStyle = TextStyle(
                        color = Color.DarkGray,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.jetbrains))
                    ),
                    colors = transparentTextFieldColors(),
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(
                            top = 16.dp,
                            bottom = 16.dp,
                            start = 8.dp,
                            end = 8.dp
                        ),
                ) {
                    Button(
                        onClick = { scope.launch { sheetState.hide() } }
                    ) {
                        Text("취소")
                    }
                    Button(
                        onClick = {
                            viewModel.addCurrentTodo()
                            scope.launch { sheetState.hide() }
                        }
                    ) {
                        Text("확인")
                    }
                }
            }
        }
    )
}

@Composable
fun TodoListCard(todoItem: TodoItem, viewModel: TodoViewModel) {
    var isCompleted by remember { mutableStateOf(false) }

    Row(
        horizontalArrangement = Arrangement.Start
    ) {
        Checkbox(
            checked = isCompleted,
            onCheckedChange = { newValue ->
                isCompleted = newValue
                viewModel.updateTodo(todoItem)
            },
            modifier = Modifier
                .padding(top = 5.dp, start = 5.dp, end = 5.dp)
        )
        TodoList(it = todoItem)
    }
}


@Composable
private fun TodoList(it: TodoItem) {
    Column(
        modifier = Modifier
            .padding(start = 10.dp, bottom = 10.dp)
    ) {
        Text(
            text = it.title,
            style = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.jetbrains))
            )
        )

        Text(
            text = it.description,
            style = TextStyle(
                color = Color.DarkGray,
                fontSize = 13.sp,
                fontFamily = FontFamily(Font(R.font.jetbrains))
            )
        )

        Text(
            text = it.formattedDate,
            style = TextStyle(
                color = Color.LightGray,
                fontSize = 10.sp,
                fontFamily = FontFamily(Font(R.font.jetbrains))
            )
        )

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Todo List",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.jetbrains))
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun transparentTextFieldColors() = TextFieldDefaults.textFieldColors(
    containerColor = Color.Transparent,
    focusedIndicatorColor = Color.LightGray,
    unfocusedIndicatorColor = Color.LightGray
)

