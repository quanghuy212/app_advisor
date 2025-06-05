package com.example.appadvisor.data.network

import com.example.appadvisor.data.model.response.StudentManageResponse
import com.example.appadvisor.data.model.response.StudentResponseDTO
import com.example.appadvisor.data.model.response.StudentTranscriptResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AdvisorApiService {

    @GET("/api/advisors/students")
    suspend fun getAllStudentsByAdvisor(): List<StudentResponseDTO>

    @GET("/api/advisors/students-manage")
    suspend fun getAllStudentsManage(): Response<List<StudentManageResponse>>

    @GET("/api/advisors/students/{studentId}")
    suspend fun getDetailsStudent(@Path("studentId") id: String): Response<StudentTranscriptResponse>
}