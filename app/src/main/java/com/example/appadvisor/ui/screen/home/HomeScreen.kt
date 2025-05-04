package com.example.appadvisor.ui.screen.home

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.appadvisor.data.model.Role
import com.example.appadvisor.ui.screen.home.HomeAdvisorScreen
import com.example.appadvisor.ui.screen.home.HomeStudentScreen

@Composable
fun HomeScreen(
    navController: NavController,
    role: Role?
) {
    when(role) {
        Role.STUDENT -> HomeStudentScreen(navController = navController)
        Role.ADVISOR -> HomeAdvisorScreen(navController = navController)
        null -> Log.e("Home Screen", "Can't identify role for login")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    val navController = rememberNavController()
    HomeScreen(navController, role = Role.ADVISOR)
}