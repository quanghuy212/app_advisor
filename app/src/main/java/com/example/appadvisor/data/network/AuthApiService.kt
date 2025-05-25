package com.example.appadvisor.data.network

import com.example.appadvisor.data.model.request.LoginRequest
import com.example.appadvisor.data.model.request.SignUpRequest
import com.example.appadvisor.data.model.response.ApiResponse
import com.example.appadvisor.data.model.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    // Sign up - METHOD: POST - ENTRY POINT: /api/auth/signup
    @POST("/api/auth/signup/student")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<ApiResponse>

    // Login - METHOD: POST - ENTRY POINT: /api/auth/login
    @POST("/api/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest) : Response<LoginResponse>
}