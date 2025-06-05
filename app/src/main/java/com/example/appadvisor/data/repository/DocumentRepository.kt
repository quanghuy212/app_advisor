package com.example.appadvisor.data.repository

import com.example.appadvisor.data.model.Document
import com.example.appadvisor.data.network.DocumentApiService
import javax.inject.Inject

class DocumentRepository @Inject constructor(
    private val documentApiService: DocumentApiService
) {
    suspend fun getDocuments(): List<Document> {
        return documentApiService.getDocuments()
    }
}