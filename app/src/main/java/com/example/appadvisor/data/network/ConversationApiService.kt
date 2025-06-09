package com.example.appadvisor.data.network

import com.example.appadvisor.data.model.Conversation
import com.example.appadvisor.data.model.response.MessageResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ConversationApiService {

    @GET("/api/conversations")
    suspend fun getAllByUser(): List<Conversation>

    @GET("/api/conversations/{conversationId}/messages")
    suspend fun getMessagesByConversationId(@Path("conversationId") conversationId: Long): List<MessageResponse>

}