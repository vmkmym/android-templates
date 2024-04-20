package com.example.composeproject.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(tableName = "todo_table")
@TypeConverters(DateConverter::class)
data class TodoItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "todo-title")
    val title: String,
    @ColumnInfo(name = "todo-desc")
    val description: String,
    @ColumnInfo(name = "date")
    val date: Date,
    @ColumnInfo(name = "dueDate")
    val dueDate: Date? = null,
    @ColumnInfo(name = "is-completed")
    var isCompleted: Boolean = false
) {
    val formattedDate: String
        get() {
            val format = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
            return format.format(date)
        }
}

class DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}