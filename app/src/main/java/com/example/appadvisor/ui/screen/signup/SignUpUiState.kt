package com.example.appadvisor.ui.screen.signup

data class SignUpUiState(
    val email: String = "",
    val name: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val role: String = "",
    val department: String = "",
    val classroom: String = "",

    var isLoading: Boolean = false,
    var isSuccess: Boolean = false
)