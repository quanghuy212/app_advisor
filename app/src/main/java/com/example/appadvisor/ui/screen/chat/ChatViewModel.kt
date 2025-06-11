package com.example.appadvisor.ui.screen.chat

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appadvisor.data.local.TokenManager
import com.example.appadvisor.data.model.Conversation
import com.example.appadvisor.data.model.enums.Role
import com.example.appadvisor.data.model.request.ConversationRequest
import com.example.appadvisor.data.model.response.StudentResponseDTO
import com.example.appadvisor.data.repository.AdvisorRepository
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
    private val advisorRepository: AdvisorRepository,
    private val tokenManager: TokenManager
): ViewModel() {

    private val _uiState = MutableStateFlow(ChatListUiState())
    val uiState: StateFlow<ChatListUiState> = _uiState

    private val _addUiState = MutableStateFlow(ConversationUiState())
    val addUiState: StateFlow<ConversationUiState> = _addUiState

    private val _editUiState = MutableStateFlow(ConversationUiState())
    val editUiState: StateFlow<ConversationUiState> = _editUiState

    private val _showEditDialog = MutableStateFlow(false)
    val showEditDialog: StateFlow<Boolean> = _showEditDialog

    private val _showDeleteDialog = MutableStateFlow(false)
    val showDeleteDialog: StateFlow<Boolean> = _showDeleteDialog

    private val _conversationToDelete = mutableStateOf<Conversation?>(null)

    private val _idEditConversation = mutableStateOf<Long?>(null)

    fun setDeleteConversation(conversation: Conversation) {
        _conversationToDelete.value = conversation
    }


    fun openDeleteDialog() {
        _showDeleteDialog.value = true
    }

    fun closeDeleteDialog() {
        _showDeleteDialog.value = false
    }

    fun openEditDialog() {
        _showEditDialog.value = true
    }

    fun closeEditDialog() {
        _showEditDialog.value = false
    }

    fun setEditUiState(conversation: Conversation) {
        _editUiState.update {
            it.copy(
                groupName = conversation.name,
                selectedParticipants = conversation.members,
            )
        }

        _idEditConversation.value = conversation.id
    }

    var role by mutableStateOf<Role?>(null)
        private set

    fun onEditGroupNameChange(name: String) {
        _editUiState.update {
            it.copy(groupName = name)
        }
    }

    fun onEditParticipantsChange(participants: List<StudentResponseDTO>) {
        _editUiState.update { currentState ->
            currentState.copy(selectedParticipants = participants)
        }
    }


    fun onParticipantsChange(participants: List<StudentResponseDTO>) {
        _addUiState.update { currentState ->
            currentState.copy(selectedParticipants = participants)
        }
    }

    fun onGroupNameChange(name: String) {
        _addUiState.update {
            it.copy(groupName = name)
        }
    }

    fun openSelectStudentDialog() {
        _addUiState.update {
            it.copy(showStudentDialog = true)
        }
    }

    fun closeSelectStudentDialog() {
        _addUiState.update {
            it.copy(showStudentDialog = false)
        }
    }

    fun openEditSelectStudentDialog() {
        _editUiState.update {
            it.copy(showStudentDialog = true)
        }
    }

    fun closeEditSelectStudentDialog() {
        _editUiState.update {
            it.copy(showStudentDialog = false)
        }
    }


    init {
        getRole()
    }

    private fun getRole() {
        viewModelScope.launch {
            role = tokenManager.getRole()?.let { Role.valueOf(it) }
        }
    }

    fun loadListStudentsByAdvisor() {
        viewModelScope.launch {
            val studentsList = advisorRepository.getAllStudentsByAdvisor()
            Log.d("Chat ViewModel","Fetch: ${studentsList.size}")

            _addUiState.update {
                it.copy(students = studentsList)
            }

            _editUiState.update {
                it.copy(
                    students = studentsList
                )
            }

        }
    }

    fun saveConversation() {
        val uiState = _addUiState.value

        val idMembers = uiState.selectedParticipants.map { it.id }

        val request = ConversationRequest(uiState.groupName, idMembers)

        viewModelScope.launch {
            if (idMembers.size < 2) {
                val result = conversationRepository.saveConversationPrivate(request)
                result.fold(
                    onSuccess = {
                        Log.d("Meeting ViewModel", "Conv")
                        getListConversation()
                    },
                    onFailure = {
                        Log.e("Meeting ViewModel", "Failed to save conversation: ${it.message}")
                    }
                )
            } else {
                val result = conversationRepository.saveConversationGroup(request)
                result.fold(
                    onSuccess = {
                        Log.d("Meeting ViewModel", "Conversation saved successfully.")
                        getListConversation()
                    },
                    onFailure = {
                        Log.e("Meeting ViewModel", "Failed to save conversation: ${it.message}")
                    }
                )
            }
        }


    }

    fun updateConversation() {
        val uiState = _editUiState.value

        val conversationId = _idEditConversation.value!!

        val request = ConversationRequest(
            groupName = uiState.groupName,
            members = uiState.selectedParticipants.map { it.id }
        )
        Log.d("Chat View Model", "Update request: $request")
        viewModelScope.launch {
            val result = conversationRepository.updateConversation(conversationId,request)
            result.fold(
                onSuccess = {
                    Log.d("Chat ViewModel", "Conversation edit successfully.")
                    getListConversation()
                },
                onFailure = {
                    Log.e("Chat ViewModel", "Failed to edit conversation: ${it.message}")
                }
            )
        }
    }

    fun deleteConversation() {
        val conversation = _conversationToDelete.value
        if (conversation == null) {
            Log.e("Chat ViewModel", "No conversation selected for deletion")
            return
        }
        Log.d("Chat ViewModel", "Delete conversation ID: $conversation.id")

        viewModelScope.launch {
            val result = conversationRepository.deleteConversation(conversationId = conversation.id)
            result.fold(
                onSuccess = {
                    Log.d("Chat ViewModel", "Delete successfully.")
                    getListConversation()
                },
                onFailure = {
                    Log.e("Chat ViewModel", "Failed to delete conversation: ${it.message}")
                }
            )
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