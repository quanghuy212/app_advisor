package com.example.appadvisor.ui.screen.transcripts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
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
import com.example.appadvisor.data.Student
import com.example.appadvisor.data.SubjectGrade


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentGradeDetailScreen(
    student: Student
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bảng điểm chi tiết") },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Thông tin sinh viên
            StudentInfoCard(student)

            Spacer(modifier = Modifier.height(16.dp))

            // Bảng điểm
            GradeTable(student.subjects)
        }
    }
}

@Composable
fun StudentInfoCard(student: Student) {

    val gpa = student.calculateGPA()

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = student.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            InfoRow(label = "Mã SV: ", value = student.id)

            Spacer(modifier = Modifier.height(4.dp))

            InfoRow(label = "Lớp: ", value = student.className)

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Điểm trung bình tích lũy: %.2f".format(gpa),
                fontWeight = FontWeight.Bold,
                color = getGpaColor(gpa)
            )
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row {
        Text(text = label, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = value)
    }
}

@Composable
fun GradeTable(subjects: List<SubjectGrade>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = "Tên môn học",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(3f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Số tín",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Điểm KTHP",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1.5f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Điểm chữ",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Subject rows
            LazyColumn {
                items(subjects) { subject ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = subject.name,
                            modifier = Modifier.weight(3f),
                            fontSize = 14.sp
                        )
                        Text(
                            text = subject.credits.toString(),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "%.1f".format(subject.finalGrade),
                            textAlign = TextAlign.Center,
                            color = getGradeColor(subject.finalGrade),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1.5f)
                        )
                        Text(
                            text = subject.getLetterGrade(),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            color = getGradeColor(subject.finalGrade),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 4.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Thống kê tổng kết
            val totalCredits = subjects.sumOf { it.credits }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Tổng số tín chỉ:",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "$totalCredits",
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

        }
    }
}


// Hàm xác định màu sắc dựa trên điểm
fun getGradeColor(grade: Float): Color {
    return when {
        grade >= 8.5f -> Color(0xFF4CAF50) // Xanh lá - A, A+
        grade >= 7.0f -> Color(0xFF2196F3) // Xanh dương - B, B+
        grade >= 5.5f -> Color(0xFFFFC107) // Vàng - C, C+
        grade >= 4.0f -> Color(0xFFFF9800) // Cam - D, D+
        else -> Color(0xFFF44336) // Đỏ - F
    }
}

fun getGpaColor(gpa: Float): Color {
    return when {
        gpa >= 3.8f -> Color(0xFF4CAF50) // Xanh lá - A, A+
        gpa >= 3.0f -> Color(0xFF2196F3) // Xanh dương - B, B+
        gpa >= 2.0f -> Color(0xFFFFC107) // Vàng - C, C+
        gpa >= 1.0f -> Color(0xFFFF9800) // Cam - D, D+
        else -> Color(0xFFF44336) // Đỏ - F
    }
}

// Hàm Preview
@Preview(showBackground = true)
@Composable
fun StudentGradeDetailScreenPreview() {
    val student = Student(
        id = "SV001",
        name = "Nguyễn Văn A",
        className = "CNTT2023",
        subjects = listOf(
            SubjectGrade(
                name = "Lập trình Android",
                credits = 3,
                finalGrade = 8.5f
            ),
            SubjectGrade(
                name = "Cơ sở dữ liệu",
                credits = 4,
                finalGrade = 7.8f
            ),
            SubjectGrade(
                name = "Mạng máy tính",
                credits = 3,
                finalGrade = 6.5f
            ),
            SubjectGrade(
                name = "Công nghệ phần mềm",
                credits = 4,
                finalGrade = 9.2f
            ),
            SubjectGrade(
                name = "Trí tuệ nhân tạo",
                credits = 3,
                finalGrade = 8.0f
            ),
            SubjectGrade(
                name = "Phân tích thiết kế hệ thống",
                credits = 4,
                finalGrade = 7.5f
            ),
            SubjectGrade(
                name = "Lập trình Web",
                credits = 3,
                finalGrade = 8.7f
            )
        )
    )

    MaterialTheme {
        StudentGradeDetailScreen(
            student = student
        )
    }
}