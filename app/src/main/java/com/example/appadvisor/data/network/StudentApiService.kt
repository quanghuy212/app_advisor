package com.example.appadvisor.data.network

import com.example.appadvisor.data.model.Student
import retrofit2.Response

import retrofit2.http.Body
import retrofit2.http.POST

interface StudentApiService {
    @POST("/students/add")
    suspend fun addStudent(@Body student: Student): Response<ApiResponse>
}
