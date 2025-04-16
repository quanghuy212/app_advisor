package com.example.appadvisor.data.repository

import com.example.appadvisor.data.model.Student
import com.example.appadvisor.data.network.ApiResponse
import com.example.appadvisor.data.network.StudentApiService
import javax.inject.Inject

class StudentRepository @Inject constructor(
    private val studentApiService: StudentApiService
) {

    suspend fun addStudent(student: Student): Result<ApiResponse> {
        return try {
            val response = studentApiService.addStudent(student = student)
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