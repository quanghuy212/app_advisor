package com.example.appadvisor.ui.screen.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskBottomSheet(
    calendarViewModel: CalendarViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
    onSave: () -> Unit
) {

    // Edit task ui state
    val editTaskUiState by calendarViewModel.editTaskUiState.collectAsState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Thêm Task",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = editTaskUiState!!.title,
                onValueChange = calendarViewModel::onEditTitleChange,
                label = { Text("Tiêu đề") },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = editTaskUiState!!.description,
                onValueChange = calendarViewModel::onEditDescriptionChange,
                label = { Text("Mô tả") },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            )

            Spacer(modifier = Modifier.height(16.dp))

            DatePickerField(
                selectedDate = editTaskUiState!!.startDate,
                onDateSelected = calendarViewModel::onStartDateChange
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TimePickerField(
                    label = "Bắt đầu",
                    time = editTaskUiState!!.startTime,
                    onTimeSelected = calendarViewModel::onEditStartTimeChange,
                    modifier = Modifier.weight(1f)
                )
                TimePickerField(
                    label = "Kết thúc",
                    time = editTaskUiState!!.endTime,
                    onTimeSelected = calendarViewModel::onEditEndTimeChange,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    onSave()
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Lưu")
            }
        }
    }
}

