package com.example.appadvisor.ui.screen.appointment

import com.example.appadvisor.data.Student
import com.example.appadvisor.data.model.enums.MeetingStatus
import com.example.appadvisor.data.model.response.StudentResponseDTO
import java.time.LocalDate
import java.time.LocalTime

data class MeetingUiState(
    val title: String = "",
    val description: String = "",
    val status: MeetingStatus = MeetingStatus.PLANNED,
    val meetingDate: LocalDate = LocalDate.now(),
    val startTime: LocalTime = LocalTime.now(),
    val endTime: LocalTime = LocalTime.now().plusHours(1),
    val selectedParticipants: List<StudentResponseDTO> = emptyList(),
    val students: List<StudentResponseDTO> = emptyList()
)

