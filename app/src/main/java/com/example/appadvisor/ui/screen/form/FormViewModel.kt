package com.example.appadvisor.ui.screen.form

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appadvisor.data.local.TokenManager
import com.example.appadvisor.data.model.enums.Role
import com.example.appadvisor.data.repository.DocumentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FormViewModel @Inject constructor(
    private val documentRepository: DocumentRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(FormUiState())
    val uiState: StateFlow<FormUiState> = _uiState

    fun getDocuments() {
        viewModelScope.launch {

            _uiState.update { it.copy(isLoading = true, isSuccess = false) }

            val documents = documentRepository.getDocuments()
            Log.d("Form viewmodel","List document: $documents")
            if (documents.isEmpty()) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isSuccess = false
                    )
                }
            }

            _uiState.update {
                it.copy(
                    documents = documents,
                    isLoading = false,
                    isSuccess = true
                )
            }
        }
    }

}