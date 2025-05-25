package com.example.appadvisor.data.network

import com.example.appadvisor.data.model.Meeting
import com.example.appadvisor.data.model.enums.MeetingStatus
import com.example.appadvisor.data.model.enums.ParticipantStatus
import com.example.appadvisor.data.model.request.MeetingRequest
import com.example.appadvisor.data.model.request.UpdateMeetingRequest
import com.example.appadvisor.data.model.response.MeetingResponse
import com.example.appadvisor.data.model.response.ParticipantInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MeetingApiService {

    @GET("/api/meetings")
    suspend fun getAllMeetingsByUser(): List<Meeting>

    @POST("/api/meetings")
    suspend fun saveMeeting(@Body meetingRequest: MeetingRequest): Response<MeetingResponse>

    @POST("/api/meetings/{meetingId}/respond")
    suspend fun respondToMeeting(
        @Path("meetingId") id: Long,
        @Body participantStatus: ParticipantStatus
    ) : Response<MeetingResponse>

    @PUT("/api/meetings/{meetingId}/status")
    suspend fun updateMeetingStatus(@Path("meetingId") id: Long) : Response<MeetingResponse>

    @DELETE("/api/meetings/{meetingId}")
    suspend fun deleteMeeting(
        @Path("meetingId") id: Long,
    ): Response<Unit>

    @PUT("/api/meetings/{meetingId}")
    suspend fun updateMeeting(
        @Path("meetingId") id: Long,
        @Body updateMeetingRequest: UpdateMeetingRequest
    ): Response<MeetingResponse>


}