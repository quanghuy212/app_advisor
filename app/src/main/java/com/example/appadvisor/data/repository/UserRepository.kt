package com.example.appadvisor.data.repository

import com.example.appadvisor.data.model.response.UserResponse
import com.example.appadvisor.data.network.UserApiService
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userApiService: UserApiService
) {

    suspend fun getCurrentUser(): Result<UserResponse> {
        return try {
            val response = userApiService.getCurrentUser()
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Get failed: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}