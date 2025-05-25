package com.example.appadvisor.data.repository

import android.util.Log
import com.example.appadvisor.data.model.Meeting
import com.example.appadvisor.data.model.enums.MeetingStatus
import com.example.appadvisor.data.model.enums.ParticipantStatus
import com.example.appadvisor.data.model.request.MeetingRequest
import com.example.appadvisor.data.model.request.UpdateMeetingRequest
import com.example.appadvisor.data.model.response.MeetingResponse
import com.example.appadvisor.data.model.response.ParticipantInfo
import com.example.appadvisor.data.network.MeetingApiService
import okhttp3.internal.notify
import javax.inject.Inject

class MeetingRepository @Inject constructor(
    private val meetingApiService: MeetingApiService
) {

    suspend fun getAllMeetingsByUser(): List<Meeting> {
        return meetingApiService.getAllMeetingsByUser()
    }

    suspend fun saveMeeting(meetingRequest: MeetingRequest): Result<MeetingResponse> {
        return try {
            val response = meetingApiService.saveMeeting(meetingRequest)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Save failed: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun respondToMeeting(id: Long, participantStatus: ParticipantStatus): Result<MeetingResponse> {
        return try {
            Log.d("Meeting Repo","Start Respond: $participantStatus")
            val response = meetingApiService.respondToMeeting(id, participantStatus)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("respond failed: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateMeetingStatus(id: Long): Result<MeetingResponse> {
        return try {
            Log.d("Meeting Repo","update status of meeting: $id")
            val response = meetingApiService.updateMeetingStatus(id)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("respond failed: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteMeeting(id: Long) : Result<Unit> {
        return try {
            val response = meetingApiService.deleteMeeting(id)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Delete failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateMeeting(id: Long, updateMeetingRequest: UpdateMeetingRequest): Result<MeetingResponse> {
        return try {
            val response = meetingApiService.updateMeeting(id, updateMeetingRequest)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Delete failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}