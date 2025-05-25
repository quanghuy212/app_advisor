package com.example.appadvisor.data.model.enums

import com.google.gson.annotations.SerializedName

enum class MeetingStatus {
    @SerializedName("PLANNED")
    PLANNED,
    @SerializedName("CONFIRMED")
    CONFIRMED,
    @SerializedName("CANCELLED")
    CANCELLED
}