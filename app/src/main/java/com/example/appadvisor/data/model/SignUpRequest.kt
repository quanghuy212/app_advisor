package com.example.appadvisor.data.model

data class SignUpStudentRequest(
    val name: String,
    val email: String,
    val password: String,
    val role: String = "Student",
    val classroom: String
)

data class SignUpAdvisorRequest(
    val name: String,
    val email: String,
    val password: String,
    val role: String = "Advisor",
    val department: String
)
