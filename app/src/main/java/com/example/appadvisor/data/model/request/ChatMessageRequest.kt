package com.example.appadvisor.data.model.request

data class ChatMessageRequest(
    val conversationId: Long,
    val message: String
)

data class EditChatRequest(
    val messageId: Long,
    val message: String
)

data class ConversationRequest(
    val groupName: String,
    val members: List<String>
)