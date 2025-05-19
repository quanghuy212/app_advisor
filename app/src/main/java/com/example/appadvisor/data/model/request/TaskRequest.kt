package com.example.appadvisor.data.model.request

import com.example.appadvisor.data.model.enums.Status
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class TaskRequest(
    private val title: String? = null,
    private val description: String? = null,
    private val status: Status? = Status.PLANNED,
    private val startDate: String? = null,
    private val startTime: String? = null,
    private val endTime: String? = null,
)
