package com.example.appadvisor.ui.screen.transcripts

import GPAProgressBar
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.appadvisor.navigation.AppScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentGradeScreen(
    navController: NavController,
    viewModel: TranscriptViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getStudentTranscripts()
    }

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Bảng Điểm",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Quay lại"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator()
                }

                uiState.transcriptResponse != null -> {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.9f),
                        shape = RoundedCornerShape(24.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            GPAProgressBar(gpaValue = uiState.transcriptResponse!!.gpa)

                            Spacer(modifier = Modifier.height(40.dp))

                            Text(
                                text = "Số tín tích lũy: ${uiState.transcriptResponse!!.credits}",
                                fontSize = 16.sp,
                                color = Color.Black
                            )

                            Spacer(modifier = Modifier.height(40.dp))

                            OutlinedButton(
                                onClick = {
                                    navController.navigate(AppScreens.ScoreDetails.route)
                                },
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .height(48.dp)
                                    .width(160.dp),
                                border = BorderStroke(1.dp, Color.Black),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = Color.Black
                                )
                            ) {
                                Text(
                                    text = "Xem chi tiết",
                                    color = Color.Black,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }

                else -> {
                    Text(text = "Không thể tải dữ liệu bảng điểm.", color = Color.Red)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StudentGradeScreenPreview() {
    val navController = rememberNavController()
    StudentGradeScreen(navController = navController)
}