 package com.example.appadvisor.ui.screen.calendar

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appadvisor.R
import com.example.appadvisor.ui.theme.AppAdvisorTheme
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskBottomSheet(
    calendarViewModel: CalendarViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
    onSave: () -> Unit
) {

    // Add task ui state
    val addTaskUiState by calendarViewModel.addTaskUiState.collectAsState()

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
                stringResource(R.string.add_task),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = addTaskUiState.title,
                onValueChange = calendarViewModel::onTitleChange,
                label = { Text(stringResource(R.string.title)) },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = addTaskUiState.description,
                onValueChange = calendarViewModel::onDescriptionChange,
                label = { Text(stringResource(R.string.des)) },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            )

            Spacer(modifier = Modifier.height(16.dp))

            DatePickerField(
                selectedDate = addTaskUiState.startDate,
                onDateSelected = calendarViewModel::onDateSelected
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TimePickerField(
                    label = "Bắt đầu",
                    time = addTaskUiState.startTime,
                    onTimeSelected = calendarViewModel::onStartTimeChange,
                    modifier = Modifier.weight(1f)
                )
                TimePickerField(
                    label = "Kết thúc",
                    time = addTaskUiState.endTime,
                    onTimeSelected = calendarViewModel::onEndTimeChange,
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

@Composable
fun DatePickerField(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val context = LocalContext.current
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val formattedDate = selectedDate.format(dateFormatter)

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                onDateSelected(LocalDate.of(year, month + 1, dayOfMonth))
            },
            selectedDate.year,
            selectedDate.monthValue - 1,
            selectedDate.dayOfMonth
        )
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Ngày:", modifier = Modifier.padding(end = 8.dp))
        TextField(
            value = formattedDate,
            onValueChange = { },
            readOnly = true,
            modifier = Modifier.weight(1f),
            shape = MaterialTheme.shapes.medium,
            trailingIcon = {
                IconButton(onClick = { datePickerDialog.show() }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Chọn ngày",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )
    }
}

@Composable
fun TimePickerField(
    label: String,
    time: LocalTime,
    onTimeSelected: (LocalTime) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val timeFormatted = time.format(DateTimeFormatter.ofPattern("HH:mm"))

    val timePickerDialog = remember {
        TimePickerDialog(
            context,
            { _, hour: Int, minute: Int ->
                onTimeSelected(LocalTime.of(hour, minute))
            },
            time.hour,
            time.minute,
            true
        )
    }

    TextField(
        value = timeFormatted,
        onValueChange = {},
        readOnly = true,
        label = { Text(label) },
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        trailingIcon = {
            IconButton(onClick = { timePickerDialog.show() }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Chọn giờ",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewAddTaskBottomSheet() {
    AppAdvisorTheme {
        AddTaskBottomSheet(
            onDismiss = {},
            onSave = {}
        )
    }
}