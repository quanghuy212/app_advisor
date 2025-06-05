package com.example.appadvisor.ui.screen.student_mng

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appadvisor.data.model.response.StudentTranscriptResponse
import com.example.appadvisor.data.repository.AdvisorRepository
import com.example.appadvisor.ui.screen.transcripts.TranscriptUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Thread.State
import javax.inject.Inject

@HiltViewModel
class StudentManageViewModel @Inject constructor(
    private val advisorRepository: AdvisorRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(StudentManageUiState())
    val uiState: StateFlow<StudentManageUiState> = _uiState

    private val _detailsUiState = MutableStateFlow(TranscriptUiState())
    val detailsState: StateFlow<TranscriptUiState> = _detailsUiState

    fun getDetailsStudent(studentId: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    isSuccess = false
                )
            }

            val result = advisorRepository.getDetailStudent(studentId = studentId)

            result.fold(
                onSuccess = {
                    _detailsUiState.value = TranscriptUiState(
                        transcriptResponse = it,
                        isLoading = false,
                        isSuccess = true
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
                    Log.e("Student Manage ViewModel", "Error fetching transcript", it)
                }
            )
        }
    }

    fun fetchStudents() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    isSuccess = false
                )
            }

            val response = advisorRepository.getStudentsManage()

            response.fold(
                onSuccess = {
                    _uiState.value = StudentManageUiState(
                        students = it,
                        isLoading = false,
                        isSuccess = true
                    )
                },
                onFailure = { it ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isSuccess = false
                        )
                    }
                    Log.e("StudentManageViewModel", "Error fetching student", it)
                }
            )


        }
    }
}
