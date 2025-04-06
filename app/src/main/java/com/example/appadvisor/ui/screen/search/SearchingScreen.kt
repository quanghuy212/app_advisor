package com.example.appadvisor.ui.screen.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appadvisor.ui.theme.AppAdvisorTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleSearchScreen() {
    // Trạng thái đơn giản cho demo
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("Tất cả") }

    // Dữ liệu demo đơn giản
    val demoData = listOf(
        Triple("GV001", "Nguyễn Văn A", "Giáo viên"),
        Triple("GV002", "Trần Thị B", "Giáo viên"),
        Triple("SV001", "Lê Văn C", "Sinh viên"),
        Triple("SV002", "Phạm Thị D", "Sinh viên"),
        Triple("SV003", "Hoàng Văn E", "Sinh viên")
    )

    // Lọc dữ liệu theo điều kiện tìm kiếm
    val filteredData = demoData.filter { (id, name, type) ->
        (searchQuery.isEmpty() || id.contains(searchQuery, ignoreCase = true) ||
                name.contains(searchQuery, ignoreCase = true)) &&
                (selectedFilter == "Tất cả" || type == selectedFilter)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tìm kiếm thông tin") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null)
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Thanh tìm kiếm
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Nhập tên hoặc mã số") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Tìm kiếm"
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Xóa"
                            )
                        }
                    }
                },
                singleLine = true,
                shape = MaterialTheme.shapes.large
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Bộ lọc đơn giản
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                FilterChip(
                    selected = selectedFilter == "Tất cả",
                    onClick = { selectedFilter = "Tất cả" },
                    label = { Text("Tất cả") }
                )
                Spacer(modifier = Modifier.width(8.dp))
                FilterChip(
                    selected = selectedFilter == "Giáo viên",
                    onClick = { selectedFilter = "Giáo viên" },
                    label = { Text("Giáo viên") }
                )
                Spacer(modifier = Modifier.width(8.dp))
                FilterChip(
                    selected = selectedFilter == "Sinh viên",
                    onClick = { selectedFilter = "Sinh viên" },
                    label = { Text("Sinh viên") }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Hiển thị kết quả tìm kiếm
            LazyColumn {
                items(filteredData.size) { index ->
                    val (id, name, type) = filteredData[index]
                    SimplePersonItem(id, name, type)
                }

                if (filteredData.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Không tìm thấy kết quả")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimplePersonItem(id: String, name: String, type: String) {
    val isTeacher = type == "Giáo viên"

    Card(
        onClick = { /* Chỉ demo */ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isTeacher)
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
            else
                MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar đơn giản
            Surface(
                modifier = Modifier.size(50.dp),
                shape = MaterialTheme.shapes.medium,
                color = if (isTeacher)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.secondary
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = name.first().toString(),
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "ID: $id",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = type,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchingScreen() {
    AppAdvisorTheme {
        SimpleSearchScreen()
    }
}