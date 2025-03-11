package com.example.appadvisor.data

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.example.appadvisor.R


// Feature Card Item
data class FeatureCard(
    val title: String,
    val iconId: Int,
    val description: String
)

val featureCards = listOf(
    FeatureCard(
        "Calendar",
        R.drawable.calendar,
        "Schedule your day"
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


