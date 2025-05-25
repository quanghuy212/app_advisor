package com.example.appadvisor.data.repository

import com.example.appadvisor.data.model.Task
import com.example.appadvisor.data.model.request.TaskRequest
import com.example.appadvisor.data.model.response.ApiResponse
import com.example.appadvisor.data.network.TaskApiService
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val taskApiService: TaskApiService
) {

    suspend fun saveTask(taskRequest: TaskRequest) : Result<ApiResponse> {
        return try {
            val response = taskApiService.saveTask(taskRequest)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Save failed: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getTasksListByUser() : List<Task> {
        return taskApiService.getTasksByUser()
    }

    suspend fun updateTask(task: Task): Result<Task> {
        return try {
            val request = TaskRequest(
                title = task.title,
                description = task.description,
                startDate = task.startDate,
                startTime = task.startTime,
                endTime = task.endTime,
                status = task.status
            )
            val response = taskApiService.updateTask(task.id, request)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Update failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteTask(taskId: Long): Result<Unit> {
        return try {
            val response = taskApiService.deleteTask(taskId)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Delete failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}