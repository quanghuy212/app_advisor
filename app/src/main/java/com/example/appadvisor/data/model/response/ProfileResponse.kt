package com.example.appadvisor.data.model.response

data class ProfileResponse(
    val id: String,
    val fullName: String,
    val email: String,
    val phone: String,
    val major: String?,        // nếu là student thì có major
    val department: String?    // nếu là advisor thì có department
)

