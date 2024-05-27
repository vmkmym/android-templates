package com.example.composeproject.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Query("SELECT * FROM habit_table")
    fun getAllHabits(): Flow<List<HabitTrackerItem>>

    @Insert
    suspend fun insertHabit(habit: HabitTrackerItem)

    @Update
    suspend fun updateHabit(habit: HabitTrackerItem)

    @Delete
    suspend fun deleteHabit(habit: HabitTrackerItem)

    @Query("Select * from habit_table where id=:id")
    fun getAHabitById(id:Long): Flow<HabitTrackerItem>
}