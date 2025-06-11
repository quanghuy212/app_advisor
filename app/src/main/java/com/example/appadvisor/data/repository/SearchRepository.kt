package com.example.appadvisor.data.repository

import com.example.appadvisor.data.model.response.SearchResponse
import com.example.appadvisor.data.network.SearchApiService
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val searchApiService: SearchApiService
) {

    suspend fun searchQuery(query: String): Result<List<SearchResponse>> {
        return try {
            val result = searchApiService.searchQuery(query)
            if (result.isSuccessful) {
                Result.success(result.body()!!)
            } else {
                Result.failure(Exception("Search failed: ${result.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}