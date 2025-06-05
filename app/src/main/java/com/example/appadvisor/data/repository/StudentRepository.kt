package com.example.appadvisor.data.repository

import com.example.appadvisor.data.model.response.StudentTranscriptResponse
import com.example.appadvisor.data.network.StudentApiService
import javax.inject.Inject

class StudentRepository @Inject constructor(
    private val studentApiService: StudentApiService
) {

    suspend fun getStudentId(): String {
        return try {
            val response = studentApiService.getStudentId()
            if (response.isSuccessful) {
                response.body()?.id ?: "Unknown"
            } else {
                "Fail"
            }
        } catch (e: Exception) {
            "Fail get Id: $e"
        }
    }

    suspend fun getStudentTranscripts(): Result<StudentTranscriptResponse> {
        return try {
            val response = studentApiService.getStudentTranscripts()
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Get failed: ${response.errorBody()?.toString()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}