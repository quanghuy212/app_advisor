package com.example.appadvisor.data.network

import com.example.appadvisor.data.model.response.StudentResponseDTO
import retrofit2.http.GET

interface AdvisorApiService {

    @GET("/api/advisors/students")
    suspend fun getAllStudentsByAdvisor() : List<StudentResponseDTO>

}