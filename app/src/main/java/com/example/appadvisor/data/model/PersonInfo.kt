package com.example.appadvisor.data.model

data class PersonInfo(
    val name: String,
    val birthday: String,
    val phone: String,
    val email: String,
    val role: Role,
    val major: String? = null, // Only student
    val batch: String? = null,  // Only student
    val department: String? = null // Only advisor
)