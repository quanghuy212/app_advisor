package com.example.appadvisor.navigation

import android.util.Log
import com.example.appadvisor.ui.screen.home.HomeScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.appadvisor.data.local.TokenManager
import com.example.appadvisor.data.model.enums.Role
import com.example.appadvisor.ui.screen.calendar.DetailsTaskScreen
import com.example.appadvisor.ui.screen.calendar.MonthlyCalendarScreen
import com.example.appadvisor.ui.screen.signup.SignUpScreen
import com.example.appadvisor.ui.screen.login.LoginScreen


@Composable
fun AppNavGraph(
    navController: NavHostController,
    tokenManager: TokenManager
) {

    var role by remember { mutableStateOf<Role?>(null) }

    LaunchedEffect(key1 = Unit) {
        role = tokenManager.getRole()?.let { Role.valueOf(it) }
    }

    val startDestination = AppScreens.Login.route


    if (role == null && startDestination != AppScreens.Login.route) return

    NavHost(navController = navController,
        startDestination = startDestination
    ) {
        // Login
        composable(AppScreens.Login.route) { LoginScreen(navController = navController) }

        // Sign up
        composable(AppScreens.SignUp.route) { SignUpScreen(navController = navController) }

        // Home
        composable(AppScreens.Home.route) { HomeScreen(navController = navController, role = role) }

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
    }
}

/*
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
        composable("create_appointment") { CreateAppointmentScreen() }*/