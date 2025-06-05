package com.example.appadvisor.ui.screen.student_mng

import com.example.appadvisor.data.model.response.StudentManageResponse

data class StudentManageUiState(
    val students: List<StudentManageResponse> = emptyList(),
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false
)
