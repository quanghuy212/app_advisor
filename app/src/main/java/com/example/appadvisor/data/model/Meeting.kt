package com.example.appadvisor.data.model

import com.example.appadvisor.data.model.enums.MeetingStatus
import com.example.appadvisor.data.model.enums.ParticipantStatus
import java.time.LocalDate
import java.time.LocalTime

data class Meeting(
    val id: Long,
    val title: String,
    val description: String,
    val meetingStatus: MeetingStatus,
    val meetingDate: String,
    val startTime: String,
    val endTime: String,
    val participants: List<ParticipantInfo>
)

data class ParticipantInfo(
    val studentId: String,
    val studentName: String,
    val status: ParticipantStatus // "PENDING", "ACCEPTED", "DECLINED"
)
