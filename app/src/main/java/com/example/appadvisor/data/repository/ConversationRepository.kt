package com.example.appadvisor.data.repository

import com.example.appadvisor.data.model.Conversation
import com.example.appadvisor.data.model.response.MessageResponse
import com.example.appadvisor.data.network.ConversationApiService
import javax.inject.Inject

class ConversationRepository @Inject constructor(
    private val conversationApiService: ConversationApiService
) {

    suspend fun getAllByUser(): List<Conversation> {
        return conversationApiService.getAllByUser()
    }

    suspend fun getMessagesByConversationId(conversationId: Long): List<MessageResponse> {
        return conversationApiService.getMessagesByConversationId(conversationId)
    }
}