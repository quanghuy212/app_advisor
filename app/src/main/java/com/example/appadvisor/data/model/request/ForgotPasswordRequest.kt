package com.example.appadvisor.data.model.request

data class ForgotPasswordRequest(
    val email: String
)

data class VerifyOtpRequest(
    val email: String,
    val otp: String
)

data class ResetPasswordRequest(
    val email: String,
    val otp: String,
    val newPassword: String
)

