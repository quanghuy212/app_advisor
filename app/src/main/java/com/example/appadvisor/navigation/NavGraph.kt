package com.example.appadvisor.navigation

import HomeScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.appadvisor.ui.component.calendar.CalendarTodoScreen
import com.example.appadvisor.ui.screen.BarcodeGenerationScreen
import com.example.appadvisor.ui.screen.ChatScreen
import com.example.appadvisor.ui.screen.InfoScreen
import com.example.appadvisor.ui.screen.OnlineForm
import com.example.appadvisor.ui.screen.ResultScreen
import com.example.appadvisor.ui.screen.SearchingScreen
import com.example.appadvisor.ui.screen.SettingScreen
import com.example.kmadvisor.ui.screen.LoginScreen
import com.example.kmadvisor.ui.screen.SignUpScreen

@Composable
fun AppNavGraph(navController: NavHostController,isLoggedIn: Boolean?) {

    val startDestination = remember {
        if (isLoggedIn == true) AppScreens.Home.route else AppScreens.Login.route
    }

    if (isLoggedIn == null) return

    NavHost(navController = navController,
        startDestination = startDestination
    ) {
        composable("login") { LoginScreen(navController = navController) }
        composable("signup") { SignUpScreen(navController = navController) }

        // Bottom navigation bar
        composable("home") { HomeScreen(navController = navController) }
        composable("search") { SearchingScreen() }
        composable("chat") { ChatScreen() }
        composable("info") { InfoScreen() }
        composable("barcode") { BarcodeGenerationScreen() }

        // Home screen
        composable("calendar") { CalendarTodoScreen() }
        composable("form") { OnlineForm() }
        composable("results") { ResultScreen() }
        composable("settings") { SettingScreen() }
    }
}