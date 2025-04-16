package com.example.appadvisor.data.repository

import com.example.appadvisor.data.model.Advisor
import com.example.appadvisor.data.network.AdvisorApiService
import com.example.appadvisor.data.network.ApiResponse
import retrofit2.Response

import javax.inject.Inject

class AdvisorRepository @Inject constructor(
    private val advisorApiService: AdvisorApiService
) {
    suspend fun addAdvisor(advisor: Advisor): Result<ApiResponse> {
        return try {
            val response = advisorApiService.addAdvisor(advisor = advisor)
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