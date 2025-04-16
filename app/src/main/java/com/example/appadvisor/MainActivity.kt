package com.example.appadvisor

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.appadvisor.navigation.AppNavGraph
import com.example.appadvisor.navigation.AppScreens
import com.example.appadvisor.ui.screen.navigation.BottomNavBar
import com.example.appadvisor.ui.theme.AppAdvisorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppAdvisorTheme {
                val navController = rememberNavController()
                val navBackStackEntry = navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry.value?.destination?.route

                // State login
                val isLoggedIn by remember { mutableStateOf(false) }
                Scaffold(
                    modifier = Modifier.imePadding(),
                    bottomBar = {
                        // If currentRoute is Login Screen or Sign up Screen, BottomNavBar isn't shown
                        if ( currentRoute !in listOf(AppScreens.Login.route, AppScreens.SignUp.route)) {
                            Box(
                                Modifier
                                    .navigationBarsPadding()
                                    .background(Color.White)
                            ) {
                                //CustomBottomNavigationBar(navController)
                                BottomNavBar(navController)
                            }
                        }

                    }

                ) { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
                        AppNavGraph(navController, isLoggedIn)
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