package com.example.appadvisor.data.model.response

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val role: String? = null,
    val token: String? = null,
    val name: String? = null,
    val id: String? = null
)
