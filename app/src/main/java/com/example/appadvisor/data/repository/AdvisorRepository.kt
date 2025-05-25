package com.example.appadvisor.data.repository

import android.util.Log
import com.example.appadvisor.data.model.response.StudentResponseDTO
import com.example.appadvisor.data.network.AdvisorApiService
import javax.inject.Inject

class AdvisorRepository @Inject constructor(
    private val advisorApiService: AdvisorApiService
) {

    suspend fun getAllStudentsByAdvisor(): List<StudentResponseDTO> {
        return advisorApiService.getAllStudentsByAdvisor()
    }
}