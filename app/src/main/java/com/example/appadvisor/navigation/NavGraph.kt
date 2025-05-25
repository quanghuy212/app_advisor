package com.example.appadvisor.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.appadvisor.data.local.TokenManager
import com.example.appadvisor.ui.screen.appointment.AddMeetingScreen
import com.example.appadvisor.ui.screen.appointment.DetailsMeetingScreen
import com.example.appadvisor.ui.screen.appointment.MeetingScreen
import com.example.appadvisor.ui.screen.barcode.BarcodeGeneratorScreen
import com.example.appadvisor.ui.screen.calendar.DetailsTaskScreen
import com.example.appadvisor.ui.screen.calendar.MonthlyCalendarScreen
import com.example.appadvisor.ui.screen.home.HomeScreen
import com.example.appadvisor.ui.screen.login.LoginScreen
import com.example.appadvisor.ui.screen.signup.SignUpScreen


@Composable
fun AppNavGraph(
    navController: NavHostController,
    tokenManager: TokenManager
) {

    NavHost(navController = navController,
        startDestination = AppScreens.Login.route
    ) {
        // Login
        composable(AppScreens.Login.route) { LoginScreen(navController = navController) }

        // Sign up
        composable(AppScreens.SignUp.route) { SignUpScreen(navController = navController) }

        // Home
        composable(AppScreens.Home.route) { HomeScreen(navController = navController) }

        // Barcode
        composable(AppScreens.Barcode.route) {
            BarcodeGeneratorScreen(navController)
        }

        // Calendar
        composable(AppScreens.Calendar.route) { MonthlyCalendarScreen(navController = navController) }

        // Details Task
        composable(
            route = AppScreens.TaskDetails.route,
            arguments = listOf(navArgument("taskId") { type = NavType.LongType})
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getLong("taskId")
            Log.d("NavGraph", "Navigating to DetailsTaskScreen with taskId = $taskId")

            taskId?.let {
                DetailsTaskScreen(taskId = it, onBackClick = {
                    navController.popBackStack()
                })
            } ?: Log.e("NavGraph", "taskId is null or invalid in route params")
        }

        // Meeting
        composable(route = AppScreens.Meeting.route) {
            MeetingScreen(
                navController = navController,
                onNavigateToCreate = { navController.navigate(AppScreens.CreateMeeting.route) }
            )
        }
        // Add Meeting
        composable(route = AppScreens.CreateMeeting.route) {
            AddMeetingScreen(
                onBack = { navController.popBackStack() }
            )
        }
        // Details Meeting
        composable(
            route = AppScreens.MeetingDetails.route,
            arguments = listOf(navArgument("meetingId") {type = NavType.LongType})
        ) { backStackEntry ->
            val meetingId = backStackEntry.arguments?.getLong("meetingId")
            Log.d("NavGraph", "Navigating to DetailsMeeting with meetingId = $meetingId")

            meetingId?.let {
                DetailsMeetingScreen(meetingId = meetingId)
            }
        }
    }
}

/*
        //composable("search") { SearchingScreen() }
        composable("chat") { ChatScreen() }
        // Chua xu li Info Screen
        //composable("info") { InfoScreen() }
        composable("barcode") { BarcodeGeneratorScreen(navController = navController) }

        //composable("form") { OnlineForm() }
        composable("results") { StudentGradeScreen(navController = navController,student = student) }
        composable("settings") { SettingsScreen() }

        composable("score_details") { StudentGradeDetailScreen(student) }
        composable("create_appointment") { CreateAppointmentScreen() }*/