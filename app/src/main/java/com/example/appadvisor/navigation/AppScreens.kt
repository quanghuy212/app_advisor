package com.example.appadvisor.navigation

sealed class AppScreens(val route: String) {

    object Login: AppScreens(route = "login")
    object SignUp: AppScreens(route = "signup")

    // Bottom Navigation Screen
    object Home: AppScreens(route = "home")
    object Search: AppScreens(route = "search")
    object Chat: AppScreens(route = "chat")
    object Info: AppScreens(route = "info")

    object Barcode: AppScreens(route = "barcode")
    object Calendar: AppScreens(route = "calendar")
    object Form: AppScreens(route = "form")
    object Settings: AppScreens(route = "settings")
}