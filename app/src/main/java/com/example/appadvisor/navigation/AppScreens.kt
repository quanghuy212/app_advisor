package com.example.appadvisor.navigation

sealed class AppScreens(val route: String) {

    data object Login: AppScreens(route = "login")
    data object SignUp: AppScreens(route = "signup")

    // Bottom Navigation Screen
    data object Home: AppScreens(route = "home")
    data object Search: AppScreens(route = "search")

    data object Chat: AppScreens(route = "chat")
    data object DetailsChat: AppScreens(route = "chatDetails/{conversationId}") {
        fun withId(id: Long) = "chatDetails/$id"
    }
    data object AddChat: AppScreens(route = "add_chat")

    data object Info: AppScreens(route = "info")

    data object Barcode: AppScreens(route = "barcode")

    data object Calendar: AppScreens(route = "calendar")
    data object TaskDetails: AppScreens(route = "taskDetails/{taskId}") {
        fun withId(id: Long) = "taskDetails/$id"
    }
    data object Form: AppScreens(route = "form")
    data object Settings: AppScreens(route = "settings")

    data object Result: AppScreens(route = "result")
    data object ScoreDetails: AppScreens(route = "score_details")

    data object Meeting: AppScreens(route = "meeting")
    data object MeetingDetails: AppScreens(route = "meetingDetails/{meetingId}") {
        fun withId(id: Long) = "meetingDetails/$id"
    }
    data object CreateMeeting: AppScreens(route = "create_meeting")

    data object StudentManage: AppScreens(route = "student-manage")

    data object ScoreDetailsByAdvisor: AppScreens(route = "score_details/{studentId}") {
        fun withId(studentId: String) = "score_details/$studentId"
    }

    data object ForgotPassword: AppScreens(route = "forgot-pass")
}