package com.example.appadvisor.ui.screen.chat

import com.example.appadvisor.data.model.Conversation
import com.example.appadvisor.data.model.response.StudentResponseDTO

data class ChatListUiState(
    val conversationList: List<Conversation> = emptyList(),
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false
)
