package com.example.appadvisor.data.network

import com.example.appadvisor.data.model.Task
import com.example.appadvisor.data.model.request.TaskRequest
import com.example.appadvisor.data.model.response.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TaskApiService {
    @POST("/api/tasks")
    suspend fun saveTask(@Body taskRequest: TaskRequest) : Response<ApiResponse>

    @GET("/api/tasks")
    suspend fun getTasksByUser(): List<Task>

    @PUT("/api/tasks/{id}")
    suspend fun updateTask(
        @Path("id") id: Long,
        @Body request: TaskRequest
    ): Response<Task>

    @DELETE("/api/tasks/{id}")
    suspend fun deleteTask(
        @Path("id") id: Long
    ): Response<Unit>
}

