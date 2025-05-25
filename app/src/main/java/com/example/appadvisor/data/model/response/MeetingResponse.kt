package com.example.appadvisor.data.model.response

import com.example.appadvisor.data.model.enums.MeetingStatus
import com.example.appadvisor.data.model.enums.ParticipantStatus
import java.time.LocalDate
import java.time.LocalTime

data class ParticipantInfo(
    val studentId: String,
    val studentName: String,
    val status: ParticipantStatus
)

data class MeetingResponse(
    val id: Long,
    val title: String,
    val description: String,
    val meetingStatus: MeetingStatus,
    val meetingDate: String,
    val startTime: String,
    val endTime: String,
    val createdAt: String,
    val advisorId: String,
    val participants: List<ParticipantInfo>
)

