package com.example.appadvisor.ui.screen.calendar

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appadvisor.ui.theme.AppAdvisorTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTaskScreen(
    taskId: Long,
    calendarViewModel: CalendarViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {}
) {
    Log.d("DetailsTaskScreen", "Rendering with taskId = $taskId")

    val allTasks by calendarViewModel.allTasks.collectAsState()
    Log.d("DetailsTaskScreen", "Total tasks loaded: ${allTasks.size}")
    val task = allTasks.find { it.id == taskId }
    Log.d("DetailsTaskScreen", "Matched task: $task")

    val showEditTaskBottomSheet by calendarViewModel.showEditTaskBottomSheet.collectAsState()
    val showDeleteConfirmDialog by calendarViewModel.showDeleteConfirmDialog.collectAsState()

    LaunchedEffect(Unit) {
        if (allTasks.isEmpty()) {
            Log.d("DetailsTaskScreen", "Triggering loadTasks() inside DetailsTaskScreen")
            calendarViewModel.loadTasks()
        }
    }

    if (task == null) {
        Log.e("DetailsTaskScreen", "Task not found! taskId: $taskId")
        Log.e("DetailsTaskScreen", "Available task IDs: ${allTasks.map { it.id }}")
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chi tiết công việc") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Quay lại")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            calendarViewModel.setEditTask(task!!)
                            calendarViewModel.toggleEditTaskBottomSheet()
                        }
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = "Chỉnh sửa")
                    }
                }

            )
        }

    ) { padding ->
        if (task != null) {
            Card(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    if (task.description.isNotBlank()) {
                        Text(
                            text = task.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    InfoRow(label = "Trạng thái", value = task.status.name)
                    task.startDate?.let { InfoRow("Ngày bắt đầu", it) }
                    task.startTime?.let { InfoRow("Giờ bắt đầu", it) }
                    task.endTime?.let { InfoRow("Giờ kết thúc", it) }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { calendarViewModel.showConfirmDeleteDialog() },
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("Xóa", color = Color.White)
                    }

                    if (showDeleteConfirmDialog) {
                        AlertDialog(
                            onDismissRequest = { calendarViewModel.closeConfirmDeleteTaskDialog() },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        calendarViewModel.deleteTask(taskId)
                                        calendarViewModel.closeConfirmDeleteTaskDialog()
                                        onBackClick()
                                    }) {
                                    Text("Xóa", color = Color.Red)
                                }
                            },
                            dismissButton = {
                                TextButton(
                                    onClick = { calendarViewModel.closeConfirmDeleteTaskDialog() }
                                ) {
                                    Text("Hủy")
                                }
                            },
                            title = { Text("Xác nhận xóa") },
                            text = { Text("Bạn có chắc muốn xóa công việc này không?") }
                        )
                    }
                }
            }



            if (showEditTaskBottomSheet) {
                EditTaskBottomSheet(
                    onDismiss = {
                        calendarViewModel.closeEditTaskBottomSheet()
                    },
                    onSave = {
                        calendarViewModel.updateTask(taskId)
                        calendarViewModel.closeEditTaskBottomSheet()
                    }
                )
            }

        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Không tìm thấy công việc.", color = Color.Red)
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0xFF3366FF)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDetailsTaskScreen() {
    AppAdvisorTheme {

    }
}
