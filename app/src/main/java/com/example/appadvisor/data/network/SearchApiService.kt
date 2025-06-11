package com.example.appadvisor.data.network

import com.example.appadvisor.data.model.response.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiService {
    @GET("/api/search")
    suspend fun searchQuery(@Query("query") query: String): Response<List<SearchResponse>>
}