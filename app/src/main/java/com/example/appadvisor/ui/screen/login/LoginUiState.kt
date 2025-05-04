package com.example.appadvisor.ui.screen.login

data class LoginUiState(

    // Input form
    val email: String = "",
    val password: String = "",

    //
    val role: String? = null,
    // State UI
    val isPasswordVisible: Boolean = false,
    // State login
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false
)
