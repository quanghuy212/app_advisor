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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.appadvisor.ui.theme.AppAdvisorTheme

@Composable
fun StudentListScreen(classId: String, navController: NavController) {
    val students = getStudentsForClass(classId)

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Lớp: $classId", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        StudentTableScreen(students = students) { student ->
            navController.navigate("student_detail/${student.id}")
        }
    }
}

@Composable
fun StudentTableScreen(students: List<Student>, onItemClick: (Student) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize().padding(4.dp)) {
        // 🔍 Thanh tìm kiếm
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Tìm kiếm sinh viên...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            singleLine = true,
            shape = MaterialTheme.shapes.large
        )

        // 🧾 Header
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
                Text("Quê quán", modifier = Modifier.width(150.dp).padding(8.dp), fontWeight = FontWeight.Bold)
                Text("GPA", modifier = Modifier.width(80.dp).padding(8.dp), fontWeight = FontWeight.Bold)
            }
        }

        Divider()

        // 🪶 Danh sách sinh viên
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(students) { student ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onItemClick(student) }
                        .padding(vertical = 8.dp)
                ) {
                    Box(modifier = Modifier.width(100.dp).padding(start = 8.dp)) {
                        Text(text = student.id)
                    }

                    Row(modifier = Modifier.horizontalScroll(scrollState)) {
                        Text(text = student.name, modifier = Modifier.width(150.dp))
                        Text(text = student.hometown, modifier = Modifier.width(150.dp))
                        Text(text = String.format("%.2f", student.gpa), modifier = Modifier.width(80.dp))
                    }
                }
                Divider()
            }
        }
    }
}



data class Student(
    val id: String,
    val name: String,
    val dob: String,
    val hometown: String,
    val gpa: Double
)


fun getStudentsForClass(classId: String): List<Student> {
    return listOf(
        Student("SV001", "Nguyễn Văn A", "01/01/2000", "Hà Nội", 3.4),
        Student("SV002", "Trần Thị B", "12/02/2000", "Nam Định", 3.6),
        Student("SV003", "Lê Văn C", "03/03/2000", "Thanh Hóa", 2.9),
        Student("SV010", "Trần Thị B", "12/02/2000", "Nam Định", 3.6),
        Student("SV011", "Trần Thị B", "12/02/2000", "Nam Định", 3.6),
        Student("SV100", "Trần Thị B", "12/02/2000", "Nam Định", 3.6),
        Student("SV101", "Trần Thị B", "12/02/2000", "Nam Định", 3.6),
        Student("SV110", "Trần Thị B", "12/02/2000", "Nam Định", 3.6),
        Student("SV110", "Trần Thị B", "12/02/2000", "Nam Định", 3.6),
        Student("SV012", "Trần Thị B", "12/02/2000", "Nam Định", 3.6),
        Student("SV021", "Trần Thị B", "12/02/2000", "Nam Định", 3.6),
        Student("SV200", "Trần Thị B", "12/02/2000", "Nam Định", 3.6),
        Student("SV033", "Trần Thị B", "12/02/2000", "Nam Định", 3.6),
        Student("SV022", "Trần Thị B", "12/02/2000", "Nam Định", 3.6),
        Student("SV014", "Trần Thị B", "12/02/2000", "Nam Định", 3.6),
        Student("SV042", "Trần Thị B", "12/02/2000", "Nam Định", 3.6),
        Student("SV041", "Trần Thị B", "12/02/2000", "Nam Định", 3.6),
        Student("SV012", "Trần Thị B", "12/02/2000", "Nam Định", 3.6),
        Student("SV021", "Trần Thị B", "12/02/2000", "Nam Định", 3.6),
        Student("SV200", "Trần Thị B", "12/02/2000", "Nam Định", 3.6),
        Student("SV033", "Trần Thị B", "12/02/2000", "Nam Định", 3.6),
        Student("SV022", "Trần Thị B", "12/02/2000", "Nam Định", 3.6),
        Student("SV014", "Trần Thị B", "12/02/2000", "Nam Định", 3.6),
        Student("SV042", "Trần Thị B", "12/02/2000", "Nam Định", 3.6),
        Student("SV041", "Trần Thị B", "12/02/2000", "Nam Định", 3.6),
        Student("SV012", "Trần Thị B", "12/02/2000", "Nam Định", 3.6),
        Student("SV021", "Trần Thị B", "12/02/2000", "Nam Định", 3.6),
        Student("SV200", "Trần Thị B", "12/02/2000", "Nam Định", 3.6),
        Student("SV033", "Trần Thị B", "12/02/2000", "Nam Định", 3.6),
        Student("SV022", "Trần Thị B", "12/02/2000", "Nam Định", 3.6),
        Student("SV014", "Trần Thị B", "12/02/2000", "Nam Định", 3.6),
        Student("SV042", "Trần Thị B", "12/02/2000", "Nam Định", 3.6),
        Student("SV041", "Trần Thị B", "12/02/2000", "Nam Định", 3.6),

    )
}


@Composable
fun StudentItemCard(student: Student, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("Mã SV: ${student.id}", fontWeight = FontWeight.Bold)
            Text("Họ tên: ${student.name}")
            Text("Quê quán: ${student.hometown}")
            Text("GPA: ${String.format("%.2f", student.gpa)}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStudentListScrenn() {
    AppAdvisorTheme {
        val navController = rememberNavController()
        StudentListScreen(classId = "CT5A", navController)
    }
}

