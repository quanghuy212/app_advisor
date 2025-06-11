package com.example.appadvisor.ui.screen.chat

data class ChatDetailsUiState(
    val messages: List<ChatMessageUiState> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val name: String = ""
)

data class ChatMessageUiState(
    val id: Long,
    val message: String,
    val isSentByMe: Boolean,
    val timestamp: String
)
