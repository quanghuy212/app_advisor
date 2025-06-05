package com.example.appadvisor.ui.screen.transcripts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.appadvisor.data.model.response.StudentTranscriptResponse
import com.example.appadvisor.data.model.response.CourseResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentGradeDetailScreen(
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
                        text = "Bảng điểm chi tiết",
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
                .padding(16.dp)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                uiState.transcriptResponse != null -> {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Thông tin
                        StudentInfoCard(uiState.transcriptResponse!!)

                        Spacer(modifier = Modifier.height(16.dp))

                        // Bảng
                        GradeTable(uiState.transcriptResponse!!.courses)
                    }
                }

                else -> {
                    Text(
                        text = "Không thể tải dữ liệu bảng điểm chi tiết.",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun StudentInfoCard(transcript: StudentTranscriptResponse) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = transcript.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(12.dp))

            InfoRow(label = "Mã SV:", value = transcript.id.uppercase())
            Spacer(modifier = Modifier.height(4.dp))
            InfoRow(label = "GPA:", value = String.format("%.2f", transcript.gpa))
            Spacer(modifier = Modifier.height(4.dp))
            InfoRow(label = "Số tín tích lũy:", value = "${transcript.credits}")
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = value,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun GradeTable(courses: List<CourseResult>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(vertical = 12.dp, horizontal = 8.dp)
            ) {
                Text(
                    text = "Tên môn học",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(3f),
                    textAlign = TextAlign.Left,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Số tín",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Điểm số",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1.5f),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Điểm chữ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1.5f),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Course rows
            LazyColumn {
                items(courses) { course ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp, horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = course.name,
                            modifier = Modifier.weight(3f),
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = course.credit.toString(),
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            modifier = Modifier.weight(1f),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = String.format("%.1f", course.finalScore),
                            textAlign = TextAlign.Center,
                            color = getGradeColor(course.finalScore),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            modifier = Modifier.weight(1.5f)
                        )
                        Text(
                            text = course.letterScore,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            color = getGradeColor(course.finalScore),
                            fontSize = 14.sp,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    if (course != courses.last()) {
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 4.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        )
                    }
                }
            }
        }
    }
}

// Hàm xác định màu sắc dựa trên điểm
fun getGradeColor(grade: Double): Color {
    return when {
        grade >= 8.5 -> Color(0xFF4CAF50) // Xanh lá - A, A+
        grade >= 7.0 -> Color(0xFF2196F3) // Xanh dương - B, B+
        grade >= 5.5 -> Color(0xFFFFC107) // Vàng - C, C+
        grade >= 4.0 -> Color(0xFFFF9800) // Cam - D, D+
        else -> Color(0xFFF44336) // Đỏ - F
    }
}

// Preview
@Preview(showBackground = true)
@Composable
fun StudentGradeDetailScreenPreview() {
    val navController = rememberNavController()
    MaterialTheme {
        StudentGradeDetailScreen(navController = navController)
    }
}