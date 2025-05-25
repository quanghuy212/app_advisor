package com.example.appadvisor.data.model

import com.example.appadvisor.data.model.enums.Status
import java.time.LocalDate

// Task
data class Task(
    val id: Long,
    val title: String,
    val description: String,
    val status: Status,
    val startDate: String? = null,
    val startTime: String? = null,
    val endTime: String? = null,
)

// Daily task
data class DailyTasks(
    val date: LocalDate,
    val tasks: List<Task>
)
