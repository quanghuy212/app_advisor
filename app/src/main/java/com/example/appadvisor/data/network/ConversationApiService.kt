package com.example.appadvisor.data.network

import com.example.appadvisor.data.model.Conversation
import com.example.appadvisor.data.model.request.ConversationRequest
import com.example.appadvisor.data.model.response.MessageConversationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ConversationApiService {

    @GET("/api/conversations")
    suspend fun getAllByUser(): List<Conversation>

    @GET("/api/conversations/{conversationId}/messages")
    suspend fun getMessagesByConversationId(@Path("conversationId") conversationId: Long): MessageConversationResponse

    @POST("/api/conversations/private")
    suspend fun saveConversationPrivate(@Body addConversationRequest: ConversationRequest): Response<Conversation>

    @POST("/api/conversations/group")
    suspend fun saveConversationGroup(@Body addConversationRequest: ConversationRequest): Response<Conversation>

    @PUT("/api/conversations/{conversationId}")
    suspend fun updateConversation(@Path("conversationId") id: Long, @Body editConversationRequest: ConversationRequest): Response<Conversation>

    @DELETE("/api/conversations/{conversationId}")
    suspend fun deleteConversation(@Path("conversationId") id: Long): Response<Unit>
}