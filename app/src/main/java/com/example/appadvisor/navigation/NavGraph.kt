package com.example.appadvisor.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.appadvisor.ui.screen.appointment.AddMeetingScreen
import com.example.appadvisor.ui.screen.appointment.DetailsMeetingScreen
import com.example.appadvisor.ui.screen.appointment.MeetingScreen
import com.example.appadvisor.ui.screen.barcode.BarcodeGeneratorScreen
import com.example.appadvisor.ui.screen.calendar.DetailsTaskScreen
import com.example.appadvisor.ui.screen.calendar.MonthlyCalendarScreen
import com.example.appadvisor.ui.screen.chat.AddConversationScreen
import com.example.appadvisor.ui.screen.chat.ChatListScreen
import com.example.appadvisor.ui.screen.chat.ChatScreen
import com.example.appadvisor.ui.screen.form.DocumentsScreen
import com.example.appadvisor.ui.screen.home.HomeScreen
import com.example.appadvisor.ui.screen.info.InfoScreen
import com.example.appadvisor.ui.screen.login.LoginScreen
import com.example.appadvisor.ui.screen.search.SearchScreen
import com.example.appadvisor.ui.screen.signup.SignUpScreen
import com.example.appadvisor.ui.screen.student_mng.DetailsStudentManage
import com.example.appadvisor.ui.screen.student_mng.StudentListScreen
import com.example.appadvisor.ui.screen.transcripts.StudentGradeDetailScreen
import com.example.appadvisor.ui.screen.transcripts.StudentGradeScreen


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun AppNavGraph(
    navController: NavHostController
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

        // Form
        composable(AppScreens.Form.route) {
            DocumentsScreen(navController, onDocumentClick = {})
        }

        // Info
        composable(AppScreens.Info.route) {
            InfoScreen()
        }

        // Result
        composable(AppScreens.Result.route) {
            StudentGradeScreen(navController)
        }

        composable(AppScreens.ScoreDetails.route) {
            StudentGradeDetailScreen(navController)
        }

        composable(AppScreens.StudentManage.route) {
            StudentListScreen(navController)
        }

        composable(
            AppScreens.ScoreDetailsByAdvisor.route,
            arguments = listOf(navArgument("studentId") {type = NavType.StringType})
        ) { backStackEntry ->
            val studentId = backStackEntry.arguments?.getString("studentId")
            Log.d("NavGraph", "Navigating to Detail Student Manage = $studentId")

            studentId?.let {
                DetailsStudentManage(studentId, navController)
            }
        }

        // Chat
        composable(AppScreens.Chat.route) {
            ChatListScreen(navController)
        }

        // Details Chat
        composable(
            route = AppScreens.DetailsChat.route,
            arguments = listOf(navArgument("conversationId") {type = NavType.LongType})
        ) { backStackEntry ->
            val conversationId = backStackEntry.arguments?.getLong("conversationId")
            Log.d("NavGraph", "Navigating to Details chat with conversationId = $conversationId")

            conversationId?.let {
                ChatScreen(conversationId = conversationId, navController = navController)
            }
        }

        composable(AppScreens.AddChat.route) {
            AddConversationScreen(navController)
        }

        composable(AppScreens.Search.route) {
            SearchScreen(navController)
        }

    }
}

/*
        //composable("search") { SearchingScreen() }


        composable("settings") { SettingsScreen() }

*/