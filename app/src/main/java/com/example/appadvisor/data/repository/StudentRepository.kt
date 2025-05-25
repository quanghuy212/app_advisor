package com.example.appadvisor.data.repository

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
}