package com.example.composeproject.model

import android.content.Context
import androidx.room.Room

object Graph {
    // Graph 객체는 앱의 데이터베이스와 리포지토리를 관리 -> 앱 전체에서 db, repo 접근 가능
    lateinit var database: TodoRoomDatabase

    val todoRepository by lazy {
        TodoRepository(todoDao = database.todoDao())
    }

    fun provide(context: Context) {
        database = Room.databaseBuilder(context, TodoRoomDatabase::class.java, "todo_database").build()
    }
}