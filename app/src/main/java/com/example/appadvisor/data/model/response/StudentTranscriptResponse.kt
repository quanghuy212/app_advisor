package com.example.appadvisor.data.model.response

data class StudentTranscriptResponse(
    val id: String,
    val name: String,
    val gpa: Double,
    val credits: Int,
    val courses: List<CourseResult>
)

data class CourseResult(
     val id: Long,
     val name: String,
     val credit: Int,
     val finalScore: Double,
     val letterScore: String
)
