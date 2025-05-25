package com.example.appadvisor.ui.screen.appointment

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appadvisor.data.model.response.StudentResponseDTO
import com.example.appadvisor.ui.screen.calendar.DatePickerField
import com.example.appadvisor.ui.screen.calendar.TimePickerField
import com.example.appadvisor.ui.theme.AppAdvisorTheme
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMeetingScreen(
    viewModel: MeetingViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val addUiState by viewModel.addUiState.collectAsState()
    val isShowSelectStudentsDialog by viewModel.isShowSelectStudentsList.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadListStudentsByAdvisor()
        Log.d("Add Meeting Screen", "UI received ${addUiState.students.size} student")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tạo cuộc hẹn") },
                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = addUiState.title,
                onValueChange = viewModel::onTitleChange,
                label = { Text("Tiêu đề") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = addUiState.description,
                onValueChange = viewModel::onDescriptionChange,
                label = { Text("Mô tả") },
                modifier = Modifier.fillMaxWidth()
            )

            DatePickerField(
                selectedDate = addUiState.meetingDate,
                onDateSelected = viewModel::onMeetingDateChange
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TimePickerField(
                    label = "Giờ bắt đầu",
                    time = addUiState.startTime,
                    onTimeSelected = viewModel::onStartTimeChange,
                    modifier = Modifier.weight(1f)
                )

                TimePickerField(
                    label = "Giờ kết thúc",
                    time = addUiState.endTime,
                    onTimeSelected = viewModel::onEndTimeChange,
                    modifier = Modifier.weight(1f)
                )
            }

            Text("Người tham gia đã chọn:")
            addUiState.selectedParticipants.forEach {
                Text("\t\t\t\t\t${it.name} \t-\t ${it.id.uppercase()}")
            }

            Button(
                onClick = { viewModel.openSelectStudentsDialog()}
            ) {
                Text("Thêm")
            }

            Button(
                onClick = {
                    viewModel.saveMeetings()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Tạo cuộc hẹn")
            }
        }

        if (isShowSelectStudentsDialog) {
            MultiSelectStudentDialog(
                students = addUiState.students,
                initiallySelected = addUiState.selectedParticipants,
                onDismiss = { viewModel.closeSelectStudentsDialog() },
                onConfirm = { viewModel.onParticipantsChange(it) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMeetingScreen1(
    uiState: MeetingUiState,
    onTitleChange: (String) -> Unit = {},
    onDescriptionChange: (String) -> Unit = {},
    onMeetingDateChange: (LocalDate) -> Unit = {},
    onStartTimeChange: (LocalTime) -> Unit = {},
    onEndTimeChange: (LocalTime) -> Unit = {},
    onBack: () -> Unit = {},
    onSubmit: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tạo cuộc hẹn") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = uiState.title,
                onValueChange = onTitleChange,
                label = { Text("Tiêu đề") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = uiState.description,
                onValueChange = onDescriptionChange,
                label = { Text("Mô tả") },
                modifier = Modifier.fillMaxWidth()
            )

            // Sử dụng DatePickerField
            DatePickerField(
                selectedDate = uiState.meetingDate,
                onDateSelected = onMeetingDateChange
            )

            // Sử dụng TimePickerField
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TimePickerField(
                    label = "Giờ bắt đầu",
                    time = uiState.startTime,
                    onTimeSelected = onStartTimeChange,
                    modifier = Modifier.weight(1f)
                )

                TimePickerField(
                    label = "Giờ kết thúc",
                    time = uiState.endTime,
                    onTimeSelected = onEndTimeChange,
                    modifier = Modifier.weight(1f)
                )
            }

            Text("Người tham gia đã chọn:")
            uiState.selectedParticipants.forEach {
                Text("\t\t\t\t\t${it.name} \t-\t ${it.id}")
            }

            Button(
                // Open select student dialog
                onClick = {

                }
            ) {
                Text("Thêm")
            }

            Button(
                onClick = onSubmit,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            ) {
                Text("Tạo cuộc hẹn")
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewAddMeetingScreen() {
    AppAdvisorTheme {
        AddMeetingScreen1(
            uiState = MeetingUiState(
                title = "Họp cố vấn học tập",
                description = "Họp nhóm A1",
                meetingDate = LocalDate.of(2025, 5, 30),
                startTime = LocalTime.of(9, 0),
                endTime = LocalTime.of(10, 0),
                selectedParticipants = listOf(
                    StudentResponseDTO(id = "1", name = "Nguyễn Văn A"),
                    StudentResponseDTO(id = "2", name = "Trần Thị B"),
                )
            )
        )
    }
}



