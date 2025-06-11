package com.example.appadvisor.data.model.response

data class MessageResponse(
    val id: Long,
    val conversationId: Long,
    val senderId: String,
    val content: String,
    val sendAt: String
)

data class MessageConversationResponse(
    val name: String,
    val messages: List<MessageResponse>
)
