package com.example.appadvisor.data.repository

import com.example.appadvisor.data.model.response.ProfileResponse
import com.example.appadvisor.data.network.InfoApiService
import javax.inject.Inject

class InfoRepository @Inject constructor(
    private val infoApiService: InfoApiService
) {
    suspend fun getProfile() : ProfileResponse {
        return infoApiService.getProfile()
    }
}