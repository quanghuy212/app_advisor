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

data class Student(
    val id: String,
    val name: String,
    val className: String,
    val subjects: List<SubjectGrade>
)

data class SubjectGrade(
    val name: String,
    val credits: Int,
    val finalGrade: Float,
    val letterGrade: String
)

val student = Student(
    id = "SV001",
    name = "Nguyễn Văn A",
    className = "CNTT2023",
    subjects = listOf(
        SubjectGrade(
            name = "Lập trình Android",
            credits = 3,
            finalGrade = 8.5f,
            letterGrade = "A"
        ),
        SubjectGrade(
            name = "Cơ sở dữ liệu",
            credits = 4,
            finalGrade = 7.8f,
            letterGrade = "B+"
        ),
        SubjectGrade(
            name = "Mạng máy tính",
            credits = 3,
            finalGrade = 6.5f,
            letterGrade = "C+"
        ),
        SubjectGrade(
            name = "Công nghệ phần mềm",
            credits = 4,
            finalGrade = 9.2f,
            letterGrade = "A+"
        ),
        SubjectGrade(
            name = "Trí tuệ nhân tạo",
            credits = 3,
            finalGrade = 8.0f,
            letterGrade = "B+"
        ),
        SubjectGrade(
            name = "Phân tích thiết kế hệ thống",
            credits = 4,
            finalGrade = 7.5f,
            letterGrade = "B"
        ),
        SubjectGrade(
            name = "Lập trình Web",
            credits = 3,
            finalGrade = 8.7f,
            letterGrade = "A"
        ),
        SubjectGrade(
            name = "Lập trình Web",
            credits = 3,
            finalGrade = 8.7f,
            letterGrade = "A"
        ),
        SubjectGrade(
            name = "Lập trình Web",
            credits = 3,
            finalGrade = 8.7f,
            letterGrade = "A"
        ),
        SubjectGrade(
            name = "Lập trình Web",
            credits = 3,
            finalGrade = 8.7f,
            letterGrade = "A"
        ),
        SubjectGrade(
            name = "Lập trình Web",
            credits = 3,
            finalGrade = 8.7f,
            letterGrade = "A"
        ),
        SubjectGrade(
            name = "Lập trình Web",
            credits = 3,
            finalGrade = 8.7f,
            letterGrade = "A"
        ),
        SubjectGrade(
            name = "Lập trình Web",
            credits = 3,
            finalGrade = 8.7f,
            letterGrade = "A"
        )
    )
)