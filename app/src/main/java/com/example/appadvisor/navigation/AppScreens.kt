package com.example.appadvisor.navigation

sealed class AppScreens(val route: String) {

    data object Login: AppScreens(route = "login")
    data object SignUp: AppScreens(route = "signup")

    // Bottom Navigation Screen
    data object Home: AppScreens(route = "home")
    data object Search: AppScreens(route = "search")
    data object Chat: AppScreens(route = "chat")
    data object Info: AppScreens(route = "info")

    data object Barcode: AppScreens(route = "barcode")

    data object Calendar: AppScreens(route = "calendar")
    data object TaskDetails: AppScreens(route = "taskDetails/{taskId}") {
        fun withId(id: Long) = "taskDetails/$id"
    }
    data object Form: AppScreens(route = "form")
    data object Settings: AppScreens(route = "settings")

    data object ScoreDetails: AppScreens(route = "score_details")
    data object CreateAppointment: AppScreens(route = "create_appointment")

}