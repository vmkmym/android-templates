@file:Suppress("DEPRECATION")

package com.example.composeproject.view

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuDefaults
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
import androidx.compose.runtime.mutableIntStateOf
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

//TODO: 정렬 버튼을 누르면 생성일 기준, 마감기한 내림차순, 마감기한 오름차순, 우선순위 내림차순, 우선순위 오름차순. 만약 마감기한이나 우선순위 기준으로 정렬을 했는데 해당값이 없음이면, 생성일 기준으로 대체.

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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
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

                // 우선 순위 드랍다운 버튼
                PriorityDropdownButton()

                // 마감 기한 버튼
                DeadlineButton(viewModel)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
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
            }
        )
        TodoList(it = todoItem)
    }
}


@Composable
private fun TodoList(it: TodoItem) {
    val priorities = listOf(" ", "!", "!!", "!!!")
    val index = it.priority % priorities.size
    val priorityText = priorities[index]

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = priorityText,
            style = TextStyle(
                color = Color.Red,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.jetbrains))
            )
        )
        Column {
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
                    text = "생성일: ${it.formattedDate}",
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
                            color = Color.LightGray,
                            fontSize = 10.sp,
                            fontFamily = FontFamily(Font(R.font.jetbrains))
                        )
                    )
                }
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
fun PriorityDropdown(
    priorities: List<String>,
    selectedPriorityIndex: Int,
    expanded: Boolean,
    onExpandedChanged: (Boolean) -> Unit,
    onPrioritySelected: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .clickable { onExpandedChanged(!expanded) }
    ) {
        Row {
            Text(
                text = priorities[selectedPriorityIndex],
                style = TextStyle(
                    color = Color.LightGray,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.jetbrains))
                )
            )
            Icon(Icons.Filled.KeyboardArrowRight, contentDescription = "우선순위 선택")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChanged(false) },
        ) {
            priorities.forEachIndexed { index, priority ->
                DropdownMenuItem(
                    text = { Text(priority) },
                    onClick = { onPrioritySelected(index) },
                    modifier = Modifier.padding(5.dp),
                    leadingIcon = {},
                    trailingIcon = {},
                    colors = MenuDefaults.itemColors(),
                    contentPadding = MenuDefaults.DropdownMenuItemContentPadding,
                )
            }
        }
    }
}

@Composable
private fun PriorityDropdownButton() {
    val priorities = listOf("없음", "낮음", "중간", "높음")
    var selectedPriorityIndex by remember { mutableIntStateOf(0) }
    var expanded by remember { mutableStateOf(false) }
    val onExpandedChanged = { newExpanded: Boolean ->
        expanded = newExpanded
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(7.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(Icons.Filled.Info, contentDescription = "우선순위 아이콘")
            Spacer(modifier = Modifier.padding(5.dp))
            Text(
                text = "우선 순위",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp
                )
            )
        }
        PriorityDropdown(
            priorities = priorities,
            selectedPriorityIndex = selectedPriorityIndex,
            expanded = expanded,
            onExpandedChanged = onExpandedChanged,
            onPrioritySelected = { index ->
                selectedPriorityIndex = index
                onExpandedChanged(false)
            }
        )
    }
}

@Composable
fun DeadlineButton(viewModel: TodoViewModel) {
    var selectedDate by remember { mutableStateOf<Date?>(null) }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(7.dp)
            .clickable { // 클릭 시 캘린더를 표시
                DatePickerDialog(context, { _, selectedYear, selectedMonth, selectedDay ->
                    calendar.set(selectedYear, selectedMonth, selectedDay)
                    selectedDate = calendar.time
                    viewModel.dueDateState = selectedDate
                }, year, month, day).show()
            },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(Icons.Filled.DateRange, contentDescription = "마감기한 아이콘")
            Spacer(modifier = Modifier.padding(5.dp))
            Text(
                text = "마감 기한",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp
                )
            )
        }
        Row {
            Text(
                /*선택한 마감 날짜를 보이게 함. 마감일 선택 안하면 없음이 기본값*/
                text = selectedDate?.let {
                    SimpleDateFormat("yyyy.MM.dd", Locale.getDefault()).format(it)
                } ?: "없음",
                style = TextStyle(
                    color = Color.LightGray,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.jetbrains))
                )
            )
            Icon(Icons.Filled.KeyboardArrowRight, contentDescription = "마감기한 선택")
        }
    }
}

