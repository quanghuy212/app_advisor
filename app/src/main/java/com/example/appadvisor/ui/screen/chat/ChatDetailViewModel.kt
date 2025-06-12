package com.example.appadvisor.ui.screen.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appadvisor.data.local.TokenManager
import com.example.appadvisor.data.local.WebSocketManager
import com.example.appadvisor.data.model.enums.Role
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

    private var currentUserId: String? = null

    var role by mutableStateOf<Role?>(null)
        private set

    init {
        getRole()
    }

    private fun getRole() {
        viewModelScope.launch {
            role = tokenManager.getRole()?.let { Role.valueOf(it) }
        }
    }

    fun toggleEmojiPicker() {
        _uiState.update { it.copy(isEmojiPickerVisible = !it.isEmojiPickerVisible) }
    }

    fun onEmojiPicked(emoji: String) {
        _uiState.update { it.copy(
            messageDraft = it.messageDraft + emoji,
            isEmojiPickerVisible = false
        ) }
    }

    fun onDraftChanged(newText: String) {
        _uiState.update { it.copy(messageDraft = newText) }
    }

    fun onMessageSent(conversationId: Long) {
        val text = _uiState.value.messageDraft.trim()
        if (text.isNotEmpty()) {
            sendMessage(conversationId, text)
            _uiState.update { it.copy(messageDraft = "") }
        }
    }




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
            val mapped = result.messages.map {
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
                    messages = mapped,
                    name = result.name
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
            println("DEBUG: Received message - ID: ${message.id}, Content: ${message.content}, SendAt: ${message.sendAt}")

            _uiState.update { currentState ->
                val existingMessageIndex = currentState.messages.indexOfFirst { it.id == message.id }
                println("DEBUG: Existing message index: $existingMessageIndex")

                if (existingMessageIndex != -1) {
                    // Message đã tồn tại - đây là edit message
                    val updatedMessages = currentState.messages.toMutableList()
                    updatedMessages[existingMessageIndex] = ChatMessageUiState(
                        id = message.id,
                        message = message.content,
                        isSentByMe = message.senderId == currentUserId,
                        timestamp = message.sendAt
                    )
                    println("DEBUG: Updated message at index $existingMessageIndex")
                    currentState.copy(messages = updatedMessages)
                } else {
                    // Message mới
                    println("DEBUG: Adding new message")
                    currentState.copy(
                        messages = currentState.messages + ChatMessageUiState(
                            id = message.id,
                            message = message.content,
                            isSentByMe = message.senderId == currentUserId,
                            timestamp = message.sendAt
                        )
                    )
                }
            }
        }
    }

    fun sendMessage(conversationId: Long, text: String) {
        webSocketManager.sendMessage(conversationId, text)
    }

    fun editMessage(messageId: Long, text: String) {
        webSocketManager.editMessage(messageId,text)
    }

    override fun onCleared() {
        super.onCleared()
        webSocketManager.disconnect()
    }
}
