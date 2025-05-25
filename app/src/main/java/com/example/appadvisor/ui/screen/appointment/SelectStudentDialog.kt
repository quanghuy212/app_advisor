package com.example.appadvisor.ui.screen.appointment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appadvisor.data.model.response.StudentResponseDTO

@Composable
fun MultiSelectStudentDialog(
    students: List<StudentResponseDTO>,
    initiallySelected: List<StudentResponseDTO> = emptyList(),
    onConfirm: (List<StudentResponseDTO>) -> Unit,
    onDismiss: () -> Unit
) {
    // State lưu các sinh viên được chọn (mặc định là danh sách truyền vào)
    val selectedStudents = remember { mutableStateListOf<StudentResponseDTO>().apply { addAll(initiallySelected) } }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Danh sách sinh viên") },
        text = {
            LazyColumn(
                modifier = Modifier.heightIn(max = 500.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(students, key = { it.id }) { student ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = selectedStudents.contains(student),
                                onCheckedChange = { checked ->
                                    if (checked) {
                                        selectedStudents.add(student)
                                    } else {
                                        selectedStudents.remove(student)
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(text = student.name, style = MaterialTheme.typography.bodyLarge)
                                Text(text = "ID: ${student.id.uppercase()}", style = MaterialTheme.typography.bodySmall, fontStyle = FontStyle.Italic)
                            }
                        }
                    }
                }
            }
        }
    ,
                confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(selectedStudents.toList())
                    onDismiss()
                }
            ) {
                Text("Xác nhận")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Hủy")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewMultiSelectStudentDialog() {
    val students = listOf(
        StudentResponseDTO(id = "1", name = "Nguyễn Văn A"),
        StudentResponseDTO(id = "2", name = "Trần Thị B"),
        StudentResponseDTO(id = "3", name = "Lê Văn C"),
        StudentResponseDTO(id = "3", name = "Lê Văn C"),
        StudentResponseDTO(id = "3", name = "Lê Văn C"),
        StudentResponseDTO(id = "3", name = "Lê Văn C")
    )

    var showDialog by remember { mutableStateOf(true) }
    var selectedStudents = remember { mutableStateListOf<StudentResponseDTO>() }

    if (showDialog) {
        MultiSelectStudentDialog(
            students = students,
            initiallySelected = selectedStudents,
            onConfirm = {
                selectedStudents.clear()
                selectedStudents.addAll(it)
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = { showDialog = true }) {
            Text("Mở dialog chọn sinh viên")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Đã chọn:")
        selectedStudents.forEach {
            Text("- ${it.name} (${it.id})")
        }
    }
}


