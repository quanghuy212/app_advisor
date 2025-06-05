package com.example.appadvisor.data.network

import com.example.appadvisor.data.model.response.StudentIdResponse
import com.example.appadvisor.data.model.response.StudentTranscriptResponse
import retrofit2.Response
import retrofit2.http.GET

interface StudentApiService {

    @GET("/api/students/id")
    suspend fun getStudentId(): Response<StudentIdResponse>

    @GET("/api/students/transcripts")
    suspend fun getStudentTranscripts(): Response<StudentTranscriptResponse>
}