package com.example.appadvisor.data.network

import com.example.appadvisor.data.model.Document
import retrofit2.http.GET

interface DocumentApiService {

    @GET("/api/documents")
    suspend fun getDocuments(): List<Document>
}