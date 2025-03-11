package com.example.appadvisor.data

import androidx.compose.ui.graphics.painter.Painter
import java.time.LocalDate




data class TodoItem(
    val id: Int,
    val title: String,
    val time: String,
    val isCompleted: Boolean = false
)

// Mở rộng để lưu trữ các công việc theo ngày
data class DateTodos(
    val date: LocalDate,
    val todos: List<TodoItem>
)