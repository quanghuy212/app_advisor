package com.example.appadvisor.ui.screen.signup

import com.example.appadvisor.data.model.Department
import com.example.appadvisor.data.model.Role

data class SignUpUiState(
    var email: String = "",
    var name: String = "",
    var password: String = "",
    var confirmPassword: String = "",
    var role: Role = Role.STUDENT,
    var department: Department = Department.ATTT,

    var isLoading: Boolean = false,
    var isSuccess: Boolean = false
)