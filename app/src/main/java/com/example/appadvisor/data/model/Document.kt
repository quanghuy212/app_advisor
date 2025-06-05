package com.example.appadvisor.data.model

import com.example.appadvisor.data.model.enums.DocumentType

// Updated Document model
data class Document(
    val id: Long,
    val title: String,
    val type: DocumentType,
    val fileSize: Long? = null,           // Kích thước file (bytes)
    val contentType: String? = null,         // "application/pdf", "image/jpeg"
    val filePath: String? = null,
    val downloadUrl: String? = null       // URL download
)
