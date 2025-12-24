package com.example.todoapp.data.model

data class Todo(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val dueDate: Long = 0L,
    val completed: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
