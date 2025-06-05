package com.example.appadvisor.data.network

import com.example.appadvisor.data.model.response.ProfileResponse
import retrofit2.http.GET

interface InfoApiService {

    @GET("/api/info")
    suspend fun getProfile(): ProfileResponse

}