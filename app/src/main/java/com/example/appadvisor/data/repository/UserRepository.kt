package com.example.appadvisor.data.repository

import android.util.Log
import com.example.appadvisor.data.local.TokenManager
import com.example.appadvisor.data.model.request.LoginRequest
import com.example.appadvisor.data.model.request.SignUpRequest
import com.example.appadvisor.data.model.response.LoginResponse
import com.example.appadvisor.data.model.response.ApiResponse
import com.example.appadvisor.data.network.AuthApiService
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val authApiService: AuthApiService,
    private val tokenManager: TokenManager
) {

    suspend fun signUp(signUpRequest: SignUpRequest): Result<ApiResponse> {
        return try {
            val response = authApiService.signUp(signUpRequest)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Registration failed: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(loginRequest: LoginRequest): Result<LoginResponse> {
        return try {
            val response = authApiService.login(loginRequest)
            if (response.isSuccessful) {

                response.body()!!.token?.let { token ->
                    Log.d("TOKEN","${tokenManager.getToken()}")
                    tokenManager.saveToken(token)
                }

                response.body()!!.role?.let { role ->
                    Log.d("ROLE","${tokenManager.getRole()}")
                    tokenManager.saveRole(role = role)
                }

                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Registration failed: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}