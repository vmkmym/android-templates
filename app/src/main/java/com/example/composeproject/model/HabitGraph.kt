package com.example.composeproject.model

import android.content.Context
import androidx.room.Room

object HabitGraph {
    lateinit var database: HabitDatabase

    val habitRepository by lazy {
        HabitRepository(database.habitDao())
    }

    fun provide(context: Context) {
        database = Room.databaseBuilder(context, HabitDatabase::class.java, "habit_database").build()
    }
}