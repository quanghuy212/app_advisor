package com.example.appadvisor.data.model.request

data class ChatMessageRequest(
    val conversationId: Long,
    val senderId: String,
    val message: String
)
