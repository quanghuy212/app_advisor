package com.example.appadvisor

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.appadvisor.data.local.LocaleUtils
import com.example.appadvisor.data.local.ThemePreference
import com.example.appadvisor.data.local.TokenManager
import com.example.appadvisor.navigation.AppNavGraph
import com.example.appadvisor.navigation.AppScreens
import com.example.appadvisor.ui.screen.navigation.BottomNavBar
import com.example.appadvisor.ui.screen.settings.SettingViewModel
import com.example.appadvisor.ui.theme.AppAdvisorTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var tokenManager: TokenManager

    override fun attachBaseContext(newBase: Context) {
        val dataStore = EntryPointAccessors.fromApplication(
            newBase.applicationContext,
            DataStoreEntryPoint::class.java
        ).dataStore()

        val lang = runBlocking {
            dataStore.data.first()[ThemePreference.LANGUAGE_KEY] ?: "vi"
        }

        val updatedContext = LocaleUtils.updateLocale(newBase, lang)
        super.attachBaseContext(updatedContext)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val viewmodel: SettingViewModel = hiltViewModel()
            val darkMode = viewmodel.darkMode.collectAsState()

            AppAdvisorTheme(darkTheme = darkMode.value) {
                val navController = rememberNavController()
                val navBackStackEntry = navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry.value?.destination?.route

                Scaffold(
                    modifier = Modifier.imePadding(),
                    bottomBar = {
                        // If currentRoute is Login Screen or Sign up Screen, BottomNavBar isn't shown
                        if ( currentRoute !in listOf(AppScreens.Login.route, AppScreens.SignUp.route, AppScreens.ForgotPassword.route)) {
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
                        AppNavGraph(navController)
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