package com.example.appadvisor.ui.screen.transcripts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
                            imageVector = androidx.compose.material.icons.Icons.Default.ArrowBack,
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
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
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

            Row {
                Text(
                    text = "Mã SV: ",
                    fontWeight = FontWeight.Medium
                )
                Text(text = student.id)
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row {
                Text(
                    text = "Lớp: ",
                    fontWeight = FontWeight.Medium
                )
                Text(text = student.className)
            }

            Spacer(modifier = Modifier.height(8.dp))

            val averageGrade = calculateAverageGrade(student.subjects)
            Text(
                text = "Điểm trung bình tích lũy: $averageGrade",
                fontWeight = FontWeight.Bold,
                color = getGradeColor(averageGrade)
            )
        }
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
                            text = subject.letterGrade,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            color = getGradeColor(subject.finalGrade),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Divider(
                        modifier = Modifier.padding(vertical = 4.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Thống kê tổng kết
            val totalCredits = subjects.sumOf { it.credits }
            val gpa = calculateAverageGrade(subjects)

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

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Điểm trung bình (GPA):",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "%.2f".format(gpa),
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold,
                    color = getGradeColor(gpa),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

// Hàm tính điểm trung bình
fun calculateAverageGrade(subjects: List<SubjectGrade>): Float {
    if (subjects.isEmpty()) return 0f

    var totalCredits = 0
    var weightedSum = 0f

    subjects.forEach { subject ->
        weightedSum += subject.finalGrade * subject.credits
        totalCredits += subject.credits
    }

    return if (totalCredits > 0) (weightedSum / totalCredits) else 0f
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
                finalGrade = 8.5f,
                letterGrade = "A"
            ),
            SubjectGrade(
                name = "Cơ sở dữ liệu",
                credits = 4,
                finalGrade = 7.8f,
                letterGrade = "B+"
            ),
            SubjectGrade(
                name = "Mạng máy tính",
                credits = 3,
                finalGrade = 6.5f,
                letterGrade = "C+"
            ),
            SubjectGrade(
                name = "Công nghệ phần mềm",
                credits = 4,
                finalGrade = 9.2f,
                letterGrade = "A+"
            ),
            SubjectGrade(
                name = "Trí tuệ nhân tạo",
                credits = 3,
                finalGrade = 8.0f,
                letterGrade = "B+"
            ),
            SubjectGrade(
                name = "Phân tích thiết kế hệ thống",
                credits = 4,
                finalGrade = 7.5f,
                letterGrade = "B"
            ),
            SubjectGrade(
                name = "Lập trình Web",
                credits = 3,
                finalGrade = 8.7f,
                letterGrade = "A"
            )
        )
    )

    MaterialTheme {
        StudentGradeDetailScreen(
            student = student
        )
    }
}