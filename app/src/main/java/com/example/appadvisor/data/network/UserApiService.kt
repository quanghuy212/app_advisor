package com.example.appadvisor.data.network

import com.example.appadvisor.data.model.Student
import com.example.appadvisor.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApiService {
    @POST("/users")
    suspend fun signUpUser(@Body user: User): Response<ApiResponse>
}