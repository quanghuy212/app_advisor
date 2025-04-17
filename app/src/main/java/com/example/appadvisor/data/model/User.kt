package com.example.appadvisor.data.model

data class User(
    val name: String,
    val email: String,
    val password: String,
    val role: String,
    val classroom: String = "",
    val department: String =""
)
