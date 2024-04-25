@file:Suppress("DEPRECATION")

package com.example.composeproject.view

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
@ExperimentalMaterial3Api
fun TodoListScreen(viewModel: TodoViewModel) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopBar(viewModel) },
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
            item {
                if (showBottomSheet) {
                    ModalSheet(sheetState, viewModel, scope) {
                        showBottomSheet = false
                    }
                }
            }
            items(todolist.value) { todoItem ->
                TodoListCard(todoItem, viewModel) { selectedTodo ->
                    viewModel.selectedTodo = selectedTodo
                    viewModel.todoTitleState = selectedTodo.title
                    viewModel.todoDescriptionState = selectedTodo.description
                    viewModel.dueDateState = selectedTodo.dueDate
                    showBottomSheet = true
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalMaterial3Api
@Composable
private fun ModalSheet(
    sheetState: SheetState,
    viewModel: TodoViewModel,
    scope: CoroutineScope,
    onDismissRequest: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var selectedDate by remember { mutableStateOf<Date?>(null) }
    var selectedTodo by remember { mutableStateOf<TodoItem?>(null) }

    if (selectedDate == null) {
        SelectYearMonthDay { year, month, day ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month - 1, day)
            selectedDate = calendar.time
        }
    }

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            onDismissRequest()
            scope.launch {
                keyboardController?.hide()
                sheetState.hide()
            }
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
                        .padding(top = 16.dp, bottom = 16.dp, start = 8.dp, end = 8.dp),
                ) {
                    // 마감기한을 선택하는 버튼 (사용자 선택사항)
                    Button(
                        onClick = {
                            viewModel.dueDateState = selectedDate
                            selectedDate = null
                        }
                    ) {
                        Text("마감기한 선택")
                    }
                    Spacer(modifier = Modifier.padding(end = 8.dp))
                    Button(
                        onClick = {
                            scope.launch {
                                keyboardController?.hide()
                                sheetState.hide()
                            }
                        }
                    ) {
                        Text("취소")
                    }
                    if (viewModel.selectedTodo != null) {
                        Button(
                            onClick = {
                                viewModel.updateTodo()
                                scope.launch {
                                    keyboardController?.hide()
                                    sheetState.hide()
                                }
                            }
                        ) {
                            Text("수정")
                        }
                    } else {
                    Button(
                        onClick = {
                            viewModel.addCurrentTodo()
                            scope.launch {
                                keyboardController?.hide()
                                sheetState.hide()
                            }
                        }
                    ) {
                        Text("확인")
                    }
                }
                }
            }
        }
    )
}

@Composable
fun TodoListCard(
    todoItem: TodoItem,
    viewModel: TodoViewModel,
    onCardClick: (TodoItem) -> Unit
) {
    var isCompleted by remember(todoItem.id) { mutableStateOf(todoItem.isCompleted) }

    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .clickable { onCardClick(todoItem) }
            .fillMaxWidth()
    ) {
        Checkbox(
            checked = isCompleted,
            onCheckedChange = { newValue ->
                isCompleted = newValue
                todoItem.isCompleted = newValue
                viewModel.updateTodo()
                if (newValue) {
                    viewModel.onTodoSelected(todoItem)
                } else {
                    viewModel.onTodoDeselected(todoItem)
                }
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
        Row {
            Text(
                text = it.formattedDate,
                style = TextStyle(
                    color = Color.LightGray,
                    fontSize = 10.sp,
                    fontFamily = FontFamily(Font(R.font.jetbrains))
                )
            )
            it.dueDate?.let { dueDate ->
                val format = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
                val formattedDueDate = format.format(dueDate)
                Text(
                    text = "   마감일: $formattedDueDate",
                    style = TextStyle(
                        color = Color.Blue,
                        fontSize = 10.sp,
                        fontFamily = FontFamily(Font(R.font.jetbrains))
                    )
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(viewModel: TodoViewModel) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Todo List",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.jetbrains))
            )
        },
        actions = {
            IconButton(
                onClick = { viewModel.deleteSelectedTodos() }
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete",
                    modifier = Modifier.padding(8.dp)
                )
            }
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

@Composable
fun SelectYearMonthDay(onDateSelected: (year: Int, month: Int, day: Int) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    LaunchedEffect(key1 = true) {
        val datePickerDialog =
            DatePickerDialog(context, { _, selectedYear, selectedMonth, selectedDay ->
                onDateSelected(selectedYear, selectedMonth + 1, selectedDay)
            }, year, month, day)

        datePickerDialog.datePicker.minDate = calendar.timeInMillis
        datePickerDialog.show()
    }
}
