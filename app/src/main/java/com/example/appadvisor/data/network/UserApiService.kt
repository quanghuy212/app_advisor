package com.example.appadvisor.data.network

import com.example.appadvisor.data.model.response.UserResponse
import retrofit2.Response
import retrofit2.http.GET

interface UserApiService {
    @GET("/api/users/id")
    suspend fun getCurrentUser(): Response<UserResponse>
}