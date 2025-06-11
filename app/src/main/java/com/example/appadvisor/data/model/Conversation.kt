package com.example.appadvisor.data.model

import com.example.appadvisor.data.model.response.StudentResponseDTO

data class Conversation(
    val id: Long,
    val name: String,
    val isGroup: Boolean,
    val members: List<StudentResponseDTO>,
)
