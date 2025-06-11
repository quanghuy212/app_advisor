package com.example.appadvisor.data

import com.example.appadvisor.R


// Feature Card Item
data class FeatureCard(
    val title: Int,
    val iconId: Int,
    val description: String
)

val studentFeatureCards = listOf(
    FeatureCard(
        R.string.feat_calendar,
        R.drawable.calendar,
        "Schedule your day"
    ),
    FeatureCard(
        R.string.feat_appointment,
        R.drawable.calendar,
        "Meeting"
    ),
    FeatureCard(
        R.string.feat_form,
        R.drawable.description,
        "Manage your tasks"
    ),
    FeatureCard(
        R.string.feat_result,
        R.drawable.target_8992467,
        "Write your thoughts"
    ),
    FeatureCard(
        R.string.feat_settings,
        R.drawable.settings_24px,
        "Customize app"
    )
)

val advisorFeatureCard = listOf(
    FeatureCard(
        R.string.feat_calendar,
        R.drawable.calendar,
        "Schedule your day"
    ),
    FeatureCard(
        R.string.feat_student_mng,
        R.drawable.baseline_view_list_24,
        "Thông tin sinh viên quản lý"
    ),
    FeatureCard(
        R.string.feat_appointment,
        R.drawable.appointment,
        "Schedule your day"
    ),
    FeatureCard(
        R.string.feat_settings,
        R.drawable.settings_24px,
        "Customize app"
    )
)


