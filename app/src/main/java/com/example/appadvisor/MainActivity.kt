package com.example.appadvisor

import HomeScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.appadvisor.ui.component.BottomNavBar
import com.example.appadvisor.ui.screen.BarcodeGenerationScreen
import com.example.appadvisor.ui.screen.CalendarScreen
import com.example.appadvisor.ui.screen.ChatScreen
import com.example.appadvisor.ui.screen.OnlineForm
import com.example.appadvisor.ui.screen.ResultScreen
import com.example.appadvisor.ui.screen.SearchingScreen
import com.example.appadvisor.ui.screen.SettingScreen
import com.example.appadvisor.ui.screen.StudentInfoScreen
//import com.example.appadvisor.ui.component.CustomBottomNavigationBar
import com.example.appadvisor.ui.theme.AppAdvisorTheme
import com.example.kmadvisor.ui.screen.LoginScreen
import com.example.kmadvisor.ui.screen.SignUpScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppAdvisorTheme {
                val navController = rememberNavController()
                val navBackStackEntry = navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry.value?.destination?.route

                Scaffold(
                    modifier = Modifier.imePadding(),
                    bottomBar = {
                        // If currentRoute is Login Screen or Sign up Screen, BottomNavBar isn't shown
                        if ( currentRoute !in listOf("login","signup")) {
                            Box(
                                Modifier
                                    .navigationBarsPadding()  // Đẩy lên tránh chồng với navbar
                                    .background(Color.White)
                            ) {
                                //CustomBottomNavigationBar(navController)
                                BottomNavBar(navController)
                            }
                        }

                    }

                ) { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
                        // Nội dung màn hình
                        NavHost(navController, startDestination = "login") {
                            // Login, Sign up
                            composable("login") { LoginScreen(navController = navController) }
                            composable("signup") { SignUpScreen(navController = navController) }

                            // Bottom navigation bar
                            composable("home") { HomeScreen(navController = navController) }
                            composable("search") { SearchingScreen() }
                            composable("chat") { ChatScreen() }
                            composable("info") { StudentInfoScreen() }
                            composable("barcode") { BarcodeGenerationScreen() }

                            // Home screen
                            composable("calendar") { CalendarScreen() }
                            composable("form") { OnlineForm() }
                            composable("results") { ResultScreen() }
                            composable("settings") { SettingScreen() }
                        }
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppAdvisorTheme {

    }
}