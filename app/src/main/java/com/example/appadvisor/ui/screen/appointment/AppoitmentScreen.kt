package com.example.appadvisor.ui.screen.appointment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.*

@Composable
fun CreateAppointmentScreen() {
    val context = LocalContext.current
    var selectedStudents by remember { mutableStateOf(listOf<String>()) }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        StudentPicker(selectedStudents = selectedStudents, onStudentsSelected = { selectedStudents = it })
        DatePicker(selectedDate = date, onDateSelected = { date = it })
        TimePicker(selectedTime = time, onTimeSelected = { time = it })

        Button(
            onClick = { /* Lưu lịch hẹn */ },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Lưu lịch hẹn")
        }
    }
}

@Composable
fun StudentPicker(selectedStudents: List<String>, onStudentsSelected: (List<String>) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    val studentList = listOf("Nguyễn Văn A", "Trần Thị B", "Lê Văn C", "Phạm Thị D")
    var searchQuery by remember { mutableStateOf("") }
    val filteredStudents = studentList.filter { it.contains(searchQuery, ignoreCase = true) }

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedStudents.joinToString(", "),
            onValueChange = {},
            label = { Text("Chọn sinh viên") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth().clickable { showDialog = true }
        )

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("Xác nhận")
                    }
                },
                text = {
                    Column {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            label = { Text("Tìm kiếm") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        LazyColumn {
                            items(filteredStudents.size) { index ->
                                val student = filteredStudents[index]
                                Row(modifier = Modifier.fillMaxWidth().padding(8.dp).clickable {
                                    val newSelection = if (selectedStudents.contains(student)) {
                                        selectedStudents - student
                                    } else {
                                        selectedStudents + student
                                    }
                                    onStudentsSelected(newSelection)
                                }) {
                                    Checkbox(
                                        checked = student in selectedStudents,
                                        onCheckedChange = {
                                            val newSelection = if (selectedStudents.contains(student)) {
                                                selectedStudents - student
                                            } else {
                                                selectedStudents + student
                                            }
                                            onStudentsSelected(newSelection)
                                        }
                                    )
                                    Text(student, modifier = Modifier.padding(start = 8.dp))
                                }
                            }
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun DatePicker(selectedDate: String, onDateSelected: (String) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    Button(onClick = {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth -> onDateSelected("$dayOfMonth/${month + 1}/$year") },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }, modifier = Modifier.padding(top = 8.dp)) {
        Text(text = if (selectedDate.isNotEmpty()) selectedDate else "Chọn ngày")
    }
}

@Composable
fun TimePicker(selectedTime: String, onTimeSelected: (String) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    Button(onClick = {
        TimePickerDialog(
            context,
            { _, hour, minute -> onTimeSelected("$hour:$minute") },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }, modifier = Modifier.padding(top = 8.dp)) {
        Text(text = if (selectedTime.isNotEmpty()) selectedTime else "Chọn giờ")
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewCreateAppointmentScreen() {
    CreateAppointmentScreen()
}