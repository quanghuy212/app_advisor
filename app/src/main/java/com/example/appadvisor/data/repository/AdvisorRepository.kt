package com.example.appadvisor.data.repository

import android.util.Log
import com.example.appadvisor.data.model.response.StudentManageResponse
import com.example.appadvisor.data.model.response.StudentResponseDTO
import com.example.appadvisor.data.model.response.StudentTranscriptResponse
import com.example.appadvisor.data.network.AdvisorApiService
import javax.inject.Inject

class AdvisorRepository @Inject constructor(
    private val advisorApiService: AdvisorApiService
) {

    suspend fun getAllStudentsByAdvisor(): List<StudentResponseDTO> {
        return advisorApiService.getAllStudentsByAdvisor()
    }

    suspend fun getStudentsManage(): Result<List<StudentManageResponse>> {
        return try {
            val response = advisorApiService.getAllStudentsManage()
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Get student manage failed: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getDetailStudent(studentId: String): Result<StudentTranscriptResponse> {
        return try {
            val response = advisorApiService.getDetailsStudent(studentId)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Get student manage failed: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}