package com.example.appadvisor.ui.screen.transcripts

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appadvisor.data.repository.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TranscriptViewModel @Inject constructor(
    private val studentRepository: StudentRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(TranscriptUiState())
    val uiState: StateFlow<TranscriptUiState> = _uiState

    fun getStudentTranscripts() {
        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(isLoading = true)

            val result = studentRepository.getStudentTranscripts()

            result.fold(
                onSuccess = { it ->
                    _uiState.value = TranscriptUiState(
                        transcriptResponse = it,
                        isSuccess = true,
                        isLoading = false
                    )
                },
                onFailure = { it ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isSuccess = false
                        )
                    }
                    // Log error
                    Log.e("TranscriptViewModel", "Error fetching transcript", it)
                }
            )
        }
    }
}