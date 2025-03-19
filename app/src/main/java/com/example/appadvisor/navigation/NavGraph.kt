package com.example.appadvisor.navigation

import HomeScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.appadvisor.data.student
import com.example.appadvisor.ui.screen.calendar.CalendarTodoScreen
import com.example.appadvisor.ui.screen.barcode.BarcodeGeneratorScreen
import com.example.appadvisor.ui.screen.chat.ChatScreen
import com.example.appadvisor.ui.screen.info.InfoScreen
import com.example.appadvisor.ui.screen.OnlineForm
import com.example.appadvisor.ui.search.SearchingScreen
import com.example.appadvisor.ui.screen.SettingsScreen
import com.example.appadvisor.ui.screen.transcripts.StudentGradeDetailScreen
import com.example.appadvisor.ui.screen.transcripts.StudentGradeScreen
import com.example.kmadvisor.ui.screen.LoginScreen
import com.example.kmadvisor.ui.screen.SignUpScreen

@Composable
fun AppNavGraph(navController: NavHostController,isLoggedIn: Boolean?) {

    val student = student
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
        composable("home") { HomeScreen(navController = navController, role = "STUDENT") }
        composable("search") { SearchingScreen() }
        composable("chat") { ChatScreen() }
        composable("info") { InfoScreen() }
        composable("barcode") { BarcodeGeneratorScreen(navController = navController) }

        // Home screen
        composable("calendar") { CalendarTodoScreen() }
        composable("form") { OnlineForm() }
        composable("results") { StudentGradeScreen(navController = navController, gpa = 3.15f) }
        composable("settings") { SettingsScreen() }

        composable("score_details") { StudentGradeDetailScreen(student) }
    }
}