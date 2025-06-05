package com.example.appadvisor.ui.screen.transcripts

import com.example.appadvisor.data.model.response.StudentTranscriptResponse

data class TranscriptUiState(
    val transcriptResponse: StudentTranscriptResponse? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false
)
