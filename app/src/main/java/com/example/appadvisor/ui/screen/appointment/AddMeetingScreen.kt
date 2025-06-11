package com.example.appadvisor.ui.screen.appointment

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appadvisor.R
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
                title = { Text(stringResource(R.string.title_add_appointment)) },
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
                label = { Text(stringResource(R.string.title)) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = addUiState.description,
                onValueChange = viewModel::onDescriptionChange,
                label = { Text(stringResource(R.string.des)) },
                modifier = Modifier.fillMaxWidth()
            )

            DatePickerField(
                selectedDate = addUiState.meetingDate,
                onDateSelected = viewModel::onMeetingDateChange
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TimePickerField(
                    label = stringResource(R.string.time_start),
                    time = addUiState.startTime,
                    onTimeSelected = viewModel::onStartTimeChange,
                    modifier = Modifier.weight(1f)
                )

                TimePickerField(
                    label = stringResource(R.string.time_end),
                    time = addUiState.endTime,
                    onTimeSelected = viewModel::onEndTimeChange,
                    modifier = Modifier.weight(1f)
                )
            }

            Text("Người tham gia đã chọn:")
            addUiState.selectedParticipants.forEach {
                //Text("\t\t\t\t\t${it.name} \t-\t ${it.id.uppercase()}")
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = it.name,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = it.id.uppercase(),
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Button(
                onClick = { viewModel.openSelectStudentsDialog()}
            ) {
                Text(stringResource(R.string.add))
            }

            Button(
                onClick = {
                    viewModel.saveMeetings()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(stringResource(R.string.save_appoint))
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



