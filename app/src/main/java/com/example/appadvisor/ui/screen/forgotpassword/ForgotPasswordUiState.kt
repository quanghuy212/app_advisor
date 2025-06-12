package com.example.appadvisor.ui.screen.forgotpassword

data class ForgotPasswordUiState(
    val isLoading: Boolean = false,

    val email: String = "",
    val isEmailValid: Boolean = true,
    val emailSent: Boolean = false,

    val otp: String = "",
    val otpVerified: Boolean = false,
    val showOtpDialog: Boolean = false,

    val newPassword: String = "",
    val confirmPassword: String = "",
    val isPasswordValid: Boolean = true,
    val passwordReset: Boolean = false,
    val showNewPasswordDialog: Boolean = false,

    val errorMessage: String? = null,
    val successMessage: String? = null
)

