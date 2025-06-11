package com.example.appadvisor.data.repository

import com.example.appadvisor.data.model.Conversation
import com.example.appadvisor.data.model.request.ConversationRequest
import com.example.appadvisor.data.model.response.MessageConversationResponse
import com.example.appadvisor.data.network.ConversationApiService
import javax.inject.Inject

class ConversationRepository @Inject constructor(
    private val conversationApiService: ConversationApiService
) {

    suspend fun getAllByUser(): List<Conversation> {
        return conversationApiService.getAllByUser()
    }

    suspend fun getMessagesByConversationId(conversationId: Long): MessageConversationResponse {
        return conversationApiService.getMessagesByConversationId(conversationId)
    }

    suspend fun saveConversationPrivate(conversationRequest: ConversationRequest): Result<Conversation> {
        return try {
            val result = conversationApiService.saveConversationPrivate(conversationRequest)
            if (result.isSuccessful) {
                Result.success(result.body()!!)
            } else {
                Result.failure(Exception("Save conversation failed: ${result.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun saveConversationGroup(conversationRequest: ConversationRequest): Result<Conversation> {
        return try {
            val result = conversationApiService.saveConversationGroup(conversationRequest)
            if (result.isSuccessful) {
                Result.success(result.body()!!)
            } else {
                Result.failure(Exception("Save conversation failed: ${result.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateConversation(conversationId: Long,editConversationRequest: ConversationRequest): Result<Conversation> {
        return try {
            val result = conversationApiService.updateConversation(conversationId,editConversationRequest)
            if (result.isSuccessful) {
                Result.success(result.body()!!)
            } else {
                Result.failure(Exception("Update conversation failed: ${result.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteConversation(conversationId: Long): Result<Unit> {
        return try {
            val result = conversationApiService.deleteConversation(conversationId)
            if (result.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Delete conversation failed: ${result.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}