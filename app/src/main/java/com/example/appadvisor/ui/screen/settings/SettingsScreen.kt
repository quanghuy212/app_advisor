package com.example.appadvisor.ui.screen.settings

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.appadvisor.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    settingViewModel: SettingViewModel = hiltViewModel()
) {
    val darkMode = settingViewModel.darkMode.collectAsState()
    val currentLang by settingViewModel.languageCode.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.feat_settings)) },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Quay lại"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Giao diện người dùng
            Text(
                text = stringResource(R.string.text_ui),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Chế độ tối
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.dark_mode_24dp),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = stringResource(R.string.text_darkmode),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        Switch(
                            checked = darkMode.value,
                            onCheckedChange = { settingViewModel.setDarkMode(it) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Ngôn ngữ và khu vực
            Text(
                text = stringResource(R.string.text_lang),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .selectableGroup()
                ) {
                    Text(
                        text = "Ngôn ngữ",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )

                    val context = LocalContext.current
                    val activity = context as? Activity
                    val languages = listOf("vi" to "Tiếng Việt", "en" to "English")

                    LaunchedEffect(Unit) {
                        settingViewModel.needRestart.collect {
                            activity?.recreate() // 🔁 chỉ gọi lại khi data đã lưu
                        }
                    }

                    languages.forEach { (code, displayName) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .selectable(
                                    selected = currentLang == code,
                                    onClick = {
                                        settingViewModel.setLanguage(code)
                                    },
                                    role = Role.RadioButton
                                )
                                .padding(horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = currentLang == code,
                                onClick = null
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(text = displayName)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Thông tin ứng dụng
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.text_aboutapp),
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = stringResource(R.string.text_version))
                        Text(text = "1.0.0")
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
