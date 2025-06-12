package com.example.appadvisor.ui.screen.student_mng

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.appadvisor.data.model.response.StudentManageResponse
import com.example.appadvisor.navigation.AppScreens
import com.example.appadvisor.ui.theme.AppAdvisorTheme

@Composable
fun StudentListScreen(
    navController: NavController,
    viewModel: StudentManageViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val filteredStudents by viewModel.filteredStudents.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchStudents()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Danh sách sinh viên", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))

        when {
            uiState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }

            uiState.isSuccess -> {
                StudentTableScreen(
                    students = filteredStudents,
                    searchQuery = searchQuery,
                    onQueryChange = viewModel::updateSearchQuery
                ) { student ->
                    navController.navigate(AppScreens.ScoreDetailsByAdvisor.withId(studentId = student.id))
                }
            }

            else -> {
                Text(
                    text = "Không thể tải danh sách sinh viên.",
                    color = Color.Red,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun StudentTableScreen(
    students: List<StudentManageResponse>,
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    onItemClick: (StudentManageResponse) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize().padding(4.dp)) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onQueryChange,
            label = { Text("Tìm kiếm sinh viên") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            singleLine = true,
            shape = MaterialTheme.shapes.large
        )

        // Table headers...
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .background(Color.LightGray)
                    .padding(8.dp)
            ) {
                Text("Mã SV", fontWeight = FontWeight.Bold)
            }
            Row(
                modifier = Modifier
                    .horizontalScroll(scrollState)
                    .background(Color.LightGray)
            ) {
                Text("Họ tên", modifier = Modifier.width(150.dp).padding(8.dp), fontWeight = FontWeight.Bold)
                Text("Điện thoại", modifier = Modifier.width(120.dp).padding(8.dp), fontWeight = FontWeight.Bold)
                Text("GPA", modifier = Modifier.width(80.dp).padding(8.dp), fontWeight = FontWeight.Bold)
            }
        }

        Divider()

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(students) { student ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onItemClick(student) }
                        .padding(vertical = 8.dp)
                ) {
                    Box(modifier = Modifier.width(100.dp).padding(start = 8.dp)) {
                        Text(text = student.id.uppercase())
                    }

                    Row(modifier = Modifier.horizontalScroll(scrollState)) {
                        Text(text = student.name, modifier = Modifier.width(150.dp))
                        Text(text = student.phone, modifier = Modifier.width(120.dp))
                        Text(text = student.gpa.toString(), modifier = Modifier.width(80.dp))
                    }
                }
                Divider()
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewStudentListScreen() {
    AppAdvisorTheme {

    }
}