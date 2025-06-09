package com.example.appadvisor.ui.screen.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appadvisor.data.local.TokenManager
import com.example.appadvisor.data.local.WebSocketManager
import com.example.appadvisor.data.model.response.MessageResponse
import com.example.appadvisor.data.repository.ConversationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatDetailViewModel @Inject constructor(
    private val conversationRepository: ConversationRepository,
    private val webSocketManager: WebSocketManager,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatDetailsUiState())
    val uiState: StateFlow<ChatDetailsUiState> = _uiState

    private val _messages = MutableStateFlow<List<MessageResponse>>(emptyList())
    val messages: StateFlow<List<MessageResponse>> = _messages

    private var currentUserId: String? = null

    fun init(conversationId: Long) {
        viewModelScope.launch {
            currentUserId = tokenManager.getId()
            loadMessages(conversationId)
            connect(conversationId)
        }
    }

    private suspend fun loadMessages(conversationId: Long) {

        _uiState.update { it.copy(isLoading = true) }

        try {
            val result = conversationRepository.getMessagesByConversationId(conversationId)
            val mapped = result.map {
                ChatMessageUiState(
                    id = it.id,
                    message = it.content,
                    isSentByMe = it.senderId == currentUserId,
                    timestamp = it.sendAt
                )
            }
            _uiState.update {
                it.copy(
                    isLoading = false,
                    messages = mapped
                )
            }
        } catch (e: Exception) {
            _uiState.update {
                it.copy(error = e.toString(), isLoading = false)
            }
        }
    }

    private fun connect(conversationId: Long) {
        webSocketManager.connect(conversationId) { message ->
            _uiState.update {
                it.copy(
                    messages = it.messages + ChatMessageUiState(
                        id = message.id,
                        message = message.content,
                        isSentByMe = message.senderId == currentUserId,
                        timestamp = message.sendAt
                    )
                )
            }
        }
    }

    fun sendMessage(conversationId: Long, text: String) {
        webSocketManager.sendMessage(conversationId, text)
    }

    override fun onCleared() {
        super.onCleared()
        webSocketManager.disconnect()
    }
}
