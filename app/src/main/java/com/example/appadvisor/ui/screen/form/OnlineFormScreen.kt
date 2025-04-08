package com.example.appadvisor.ui.screen.form

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.appadvisor.ui.theme.AppAdvisorTheme

// Model đơn giản hóa, chỉ giữ lại title và type
data class Document(
    val id: String,
    val title: String,
    val type: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentsScreen(navController: NavController, onDocumentClick: (Document) -> Unit) {
    // Danh sách văn bản đơn giản hóa
    val documents = listOf(
        Document("leave_request", "Đơn xin nghỉ phép", "Đơn"),
        Document("extended_leave", "Đơn xin nghỉ dài hạn", "Đơn"),
        Document("retake_course", "Đơn xin học lại", "Đơn"),
        Document("course_withdrawal", "Đơn xin rút môn học", "Đơn"),
        Document("change_major", "Đơn xin chuyển ngành", "Đơn"),
        Document("academic_calendar", "Lịch học tập năm 2024-2025", "Tài liệu tham khảo"),
        Document("course_catalog", "Danh mục môn học", "Tài liệu tham khảo"),
        Document("scholarship_guide", "Hướng dẫn xin học bổng", "Tài liệu hướng dẫn"),
        Document("enrollment_guide", "Hướng dẫn đăng ký môn học", "Tài liệu hướng dẫn")
    )

    var selectedType by remember { mutableStateOf<String?>(null) }
    val documentTypes = listOf("Tất cả", "Đơn", "Tài liệu tham khảo", "Tài liệu hướng dẫn")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Văn bản", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Quay lại")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Filter theo type
            ScrollableTabRow(
                selectedTabIndex = if (selectedType == null) 0 else documentTypes.indexOf(selectedType),
                modifier = Modifier.fillMaxWidth(),
                edgePadding = 16.dp
            ) {
                documentTypes.forEachIndexed { index, type ->
                    Tab(
                        selected = (selectedType == type) || (index == 0 && selectedType == null),
                        onClick = {
                            selectedType = if (index == 0) null else type
                        },
                        text = { Text(type) }
                    )
                }
            }

            // Danh sách văn bản
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                val filteredDocuments = if (selectedType == null) {
                    documents
                } else {
                    documents.filter { it.type == selectedType }
                }

                items(filteredDocuments) { document ->
                    SimpleDocumentItem(document = document, onClick = { onDocumentClick(document) })
                }
            }
        }
    }
}

@Composable
fun SimpleDocumentItem(
    document: Document,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = document.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )

                Text(
                    text = document.type,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        Divider(modifier = Modifier.fillMaxWidth())
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDocumentsScreen() {
    AppAdvisorTheme {
        val navController = rememberNavController()
        DocumentsScreen(navController, onDocumentClick = {})
    }
}