package com.example.appadvisor.data.network

import com.example.appadvisor.data.model.Advisor
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AdvisorApiService {
    @POST("/advisors/add")
    suspend fun addAdvisor(@Body advisor: Advisor): Response<ApiResponse>
}