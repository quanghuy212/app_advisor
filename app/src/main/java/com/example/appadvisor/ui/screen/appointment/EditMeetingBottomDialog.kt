package com.example.appadvisor.ui.screen.appointment

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appadvisor.ui.screen.calendar.DatePickerField
import com.example.appadvisor.ui.screen.calendar.TimePickerField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditMeetingBottomDialog(
    viewModel: MeetingViewModel = hiltViewModel(),
    onDismissRequest: () -> Unit,
    onSave: () -> Unit
) {
    val editUiState by viewModel.editUiState.collectAsState()
    val isShowSelectStudentsDialog by viewModel.isShowSelectStudentsList.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadListStudentsByAdvisor()
        Log.d("Add Meeting Screen", "UI received ${editUiState.students.size} student")
    }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Chỉnh sửa cuộc hẹn", style = MaterialTheme.typography.titleLarge)

            OutlinedTextField(
                value = editUiState.title,
                onValueChange = viewModel::onEditTitleChange,
                label = { Text("Tiêu đề") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = editUiState.description,
                onValueChange = viewModel::onEditDescriptionChange,
                label = { Text("Mô tả") },
                modifier = Modifier.fillMaxWidth()
            )

            DatePickerField(
                selectedDate = editUiState.meetingDate,
                onDateSelected = viewModel::onEditMeetingDateChange
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TimePickerField(
                    label = "Giờ bắt đầu",
                    time = editUiState.startTime,
                    onTimeSelected = viewModel::onEditStartTimeChange,
                    modifier = Modifier.weight(1f)
                )
                TimePickerField(
                    label = "Giờ kết thúc",
                    time = editUiState.endTime,
                    onTimeSelected = viewModel::onEditEndTimeChange,
                    modifier = Modifier.weight(1f)
                )
            }

            Text("Người tham gia đã chọn:")
            editUiState.selectedParticipants.forEach {
                Text("\t\t\t\t\t${it.name} \t-\t ${it.id.uppercase()}")
            }

            Button(onClick = { viewModel.openSelectStudentsDialog() }) {
                Text("Chỉnh sửa người tham gia")
            }

            Button(
                onClick = {
                    viewModel.updateMeeting()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text("Lưu thay đổi")
            }
        }

        if (isShowSelectStudentsDialog) {
            MultiSelectStudentDialog(
                students = editUiState.students,
                initiallySelected = editUiState.selectedParticipants,
                onDismiss = { viewModel.closeSelectStudentsDialog() },
                onConfirm = { viewModel.onEditParticipantsChange(it) }
            )
        }
    }
}
