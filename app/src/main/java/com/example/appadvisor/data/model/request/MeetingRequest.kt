package com.example.appadvisor.data.model.request

import com.example.appadvisor.data.model.ParticipantInfo
import java.time.LocalDate
import java.time.LocalTime

data class MeetingRequest(
    val title: String,
    val description: String,
    val meetingDate: String,
    val startTime: String,
    val endTime: String,
    val participants: List<String>
)

data class UpdateMeetingRequest(
    val title: String,
    val description: String,
    val meetingDate: String,
    val startTime: String,
    val endTime: String,
    val participants: List<ParticipantInfo>
)
