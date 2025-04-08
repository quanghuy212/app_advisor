package com.example.appadvisor.navigation

import HomeScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.appadvisor.data.model.UserRole
import com.example.appadvisor.data.student
import com.example.appadvisor.ui.screen.appointment.CreateAppointmentScreen
import com.example.appadvisor.ui.screen.barcode.BarcodeGeneratorScreen
import com.example.appadvisor.ui.screen.calendar.CalendarTodoScreen
import com.example.appadvisor.ui.screen.chat.ChatScreen
import com.example.appadvisor.ui.screen.settings.SettingsScreen
import com.example.appadvisor.ui.screen.signup.SignUpScreen
import com.example.appadvisor.ui.screen.transcripts.StudentGradeDetailScreen
import com.example.appadvisor.ui.screen.transcripts.StudentGradeScreen
import com.example.kmadvisor.ui.screen.LoginScreen

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
        composable("home") { HomeScreen(navController = navController, role = UserRole.ADVISOR) }
        //composable("search") { SearchingScreen() }
        composable("chat") { ChatScreen() }
        // Chua xu li Info Screen
        //composable("info") { InfoScreen() }
        composable("barcode") { BarcodeGeneratorScreen(navController = navController) }

        // Home screen
        composable("calendar") { CalendarTodoScreen() }
        //composable("form") { OnlineForm() }
        composable("results") { StudentGradeScreen(navController = navController,student = student) }
        composable("settings") { SettingsScreen() }

        composable("score_details") { StudentGradeDetailScreen(student) }
        composable("create_appointment") { CreateAppointmentScreen() }
    }
}