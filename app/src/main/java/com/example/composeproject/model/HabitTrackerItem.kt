package com.example.composeproject.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(tableName = "habit_table")
@TypeConverters(DateConverter::class)
data class HabitTrackerItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "habit-title")
    val title: String,
    @ColumnInfo(name = "habit-description")
    val description: String,
    @ColumnInfo(name = "start-date")
    val startDate: Date,
    @ColumnInfo(name = "due-Date")
    val dueDate: Date? = null,
) {
    val formattedDate: String
        get() {
            val format = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
            return format.format(startDate)
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