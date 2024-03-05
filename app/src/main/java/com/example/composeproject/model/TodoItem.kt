package com.example.composeproject.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class TodoItem(
    val title: String = "",
    val description: String = "",
    var isCompleted: Boolean = false,
    val isCreatedDate: Date = Date(),
) {
    val formattedDate: String
        get() {
            val format = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
            return format.format(isCreatedDate)
        }
}
