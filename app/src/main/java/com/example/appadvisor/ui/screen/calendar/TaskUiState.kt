package com.example.appadvisor.ui.screen.calendar

import com.example.appadvisor.data.model.enums.Status
import java.time.LocalDate
import java.time.LocalTime

data class TaskUiState(
    val title: String = "",
    val description: String = "",
    val status: Status = Status.PLANNED,
    val startDate: LocalDate = LocalDate.now(),
    val startTime: LocalTime = LocalTime.now(),
    val endTime: LocalTime = startTime.plusHours(1),
)
