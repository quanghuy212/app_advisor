package com.example.appadvisor.data.repository

import com.example.appadvisor.data.model.Student
import com.example.appadvisor.data.model.User
import com.example.appadvisor.data.network.ApiResponse
import com.example.appadvisor.data.network.StudentApiService
import com.example.appadvisor.data.network.UserApiService
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userApiService: UserApiService
) {
    suspend fun signUpUser(user: User): Result<ApiResponse> {
        return try {
            val response = userApiService.signUpUser(user = user)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Registration failed: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}