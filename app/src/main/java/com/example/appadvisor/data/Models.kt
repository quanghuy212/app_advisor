package com.example.appadvisor.data

import java.time.LocalDate



data class Student(
    val id: String,
    val name: String,
    val className: String,
    val subjects: List<SubjectGrade>
) {
    fun calculateGPA(): Float {
        val totalCredits = subjects.sumOf { it.credits }
        val totalGradePoints = subjects.map { it.toGPA() * it.credits }.sum()
        return if (totalCredits > 0) totalGradePoints / totalCredits else 0f
    }
}

data class SubjectGrade(
    val name: String,
    val credits: Int,
    val finalGrade: Float
) {
    fun getLetterGrade(): String {
        return when {
            finalGrade >= 9.0f -> "A+"
            finalGrade >= 8.5f -> "A"
            finalGrade >= 7.8f -> "B+"
            finalGrade >= 7.0f -> "B"
            finalGrade >= 6.3f -> "C+"
            finalGrade >= 5.5f -> "C"
            finalGrade >= 4.8f -> "D+"
            finalGrade >= 4.0f -> "D"
            else -> "F"
        }
    }

    fun toGPA(): Float {
        return when(getLetterGrade()) {
            "A+" -> 4.0f
            "A" -> 3.8f
            "B+" -> 3.5f
            "B" -> 3.0f
            "C+" -> 2.5f
            "C" -> 2.0f
            "D+" -> 1.5f
            "D" -> 1.0f
            else -> 0.0f
        }
    }
}


val student = Student(
    id = "SV001",
    name = "Nguyễn Văn A",
    className = "CNTT2023",
    subjects = listOf(
        SubjectGrade(
            name = "Lập trình Android",
            credits = 3,
            finalGrade = 8.5f
        ),
        SubjectGrade(
            name = "Cơ sở dữ liệu",
            credits = 4,
            finalGrade = 7.8f
        ),
        SubjectGrade(
            name = "Mạng máy tính",
            credits = 3,
            finalGrade = 6.5f
        ),
        SubjectGrade(
            name = "Công nghệ phần mềm",
            credits = 4,
            finalGrade = 9.2f
        ),
        SubjectGrade(
            name = "Trí tuệ nhân tạo",
            credits = 3,
            finalGrade = 8.0f
        ),
        SubjectGrade(
            name = "Phân tích thiết kế hệ thống",
            credits = 4,
            finalGrade = 7.5f
        ),
        SubjectGrade(
            name = "Lập trình Web",
            credits = 3,
            finalGrade = 8.7f
        ),
        SubjectGrade(
            name = "Lập trình Web",
            credits = 3,
            finalGrade = 8.7f
        ),
        SubjectGrade(
            name = "Lập trình Web",
            credits = 3,
            finalGrade = 8.7f
        ),
        SubjectGrade(
            name = "Lập trình Web",
            credits = 3,
            finalGrade = 8.7f
        ),
        SubjectGrade(
            name = "Lập trình Web",
            credits = 3,
            finalGrade = 8.7f
        ),
        SubjectGrade(
            name = "Lập trình Web",
            credits = 3,
            finalGrade = 8.7f
        ),
        SubjectGrade(
            name = "Lập trình Web",
            credits = 3,
            finalGrade = 8.7f
        )
    )
)