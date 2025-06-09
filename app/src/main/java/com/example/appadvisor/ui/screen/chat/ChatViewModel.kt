package com.example.appadvisor.ui.screen.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appadvisor.data.local.TokenManager
import com.example.appadvisor.data.model.enums.Role
import com.example.appadvisor.data.repository.ConversationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val conversationRepository: ConversationRepository,
    private val tokenManager: TokenManager
): ViewModel() {

    private val _uiState = MutableStateFlow(ChatListUiState())
    val uiState: StateFlow<ChatListUiState> = _uiState

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

    fun getListConversation() {
        _uiState.update {
            it.copy(
                isLoading = true,
                isSuccess = false
            )
        }

        viewModelScope.launch {
            val conversations = conversationRepository.getAllByUser()

            _uiState.update {
                it.copy(
                    conversationList = conversations,
                    isLoading = false,
                    isSuccess = true
                )
            }
        }
    }
}