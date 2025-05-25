package com.example.appadvisor.ui.screen.appointment

import com.example.appadvisor.data.model.Meeting
import com.example.appadvisor.data.model.enums.MeetingStatus

data class MeetingScreenUiState(
    val meetings: List<Meeting> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedTab: String = MeetingStatus.PLANNED.toString()
)
