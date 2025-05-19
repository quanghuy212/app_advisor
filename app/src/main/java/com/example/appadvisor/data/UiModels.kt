package com.example.appadvisor.data

import com.example.appadvisor.R


// Feature Card Item
data class FeatureCard(
    val title: String,
    val iconId: Int,
    val description: String
)

val studentFeatureCards = listOf(
    FeatureCard(
        "Calendar",
        R.drawable.calendar,
        "Schedule your day"
    ),
    FeatureCard(
        "Appointment",
        R.drawable.calendar,
        "Appointment"
    ),
    FeatureCard(
        "Form",
        R.drawable.description,
        "Manage your tasks"
    ),
    FeatureCard(
        "Results",
        R.drawable.target_8992467,
        "Write your thoughts"
    ),
    FeatureCard(
        "Settings",
        R.drawable.settings_24px,
        "Customize app"
    )
)

val advisorFeatureCard = listOf(
    FeatureCard(
        "Calendar",
        R.drawable.calendar,
        "Schedule your day"
    ),
    FeatureCard(
        "QL Sinh Viên",
        R.drawable.baseline_view_list_24,
        "Thông tin sinh viên quản lý"
    ),
    FeatureCard(
        "Tạo lịch hẹn",
        R.drawable.appointment,
        "Schedule your day"
    ),
    FeatureCard(
        "Settings",
        R.drawable.settings_24px,
        "Customize app"
    )
)


