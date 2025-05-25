package com.example.appadvisor.data.model.response

data class ApiResponse(
    val success: Boolean,
    val message: String
)

data class StudentIdResponse(val id: String)