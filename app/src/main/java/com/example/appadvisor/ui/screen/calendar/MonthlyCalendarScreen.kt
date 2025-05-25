package com.example.appadvisor.ui.screen.calendar

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.appadvisor.data.model.DailyTasks
import com.example.appadvisor.data.model.Task
import com.example.appadvisor.data.model.enums.Status
import com.example.appadvisor.navigation.AppScreens
import com.example.appadvisor.ui.theme.AppAdvisorTheme
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.daysOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun MonthlyCalendarScreen(
    navController: NavController,
    calendarViewModel: CalendarViewModel = hiltViewModel()
) {
    val selectedDate by calendarViewModel.selectedDate.collectAsState()
    val allTasks by calendarViewModel.allTasks.collectAsState()
    val dailyTasksList by calendarViewModel.dailyTasksList.collectAsState()
    val showAddBottomSheet by calendarViewModel.showAddTaskBottomSheet.collectAsState()

    LaunchedEffect(Unit) {
        calendarViewModel.loadTasks()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F4F8))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // Calendar View
        MonthlyCalendarHeader(
            selectedDate = selectedDate,
            dailyTasksList = dailyTasksList,
            onDateSelected = calendarViewModel::onDateSelected
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Task List View
        TaskListForSelectedDate(
            selectedDate = selectedDate,
            dailyTasksList = dailyTasksList,
            onTaskToggle = { task, date ->
                calendarViewModel.toggleTaskCompletion(task, date)
            },
            onAddButtonClick = {
                calendarViewModel.toggleAddTaskBottomSheet()
            },
            onTaskClick = { task ->
                Log.d("TaskItemClick", "Navigating to taskId = ${task.id}")
                navController.navigate(AppScreens.TaskDetails.withId(task.id))
            }
        )

        if (showAddBottomSheet) {
            AddTaskBottomSheet(
                onDismiss = {
                    calendarViewModel.closeAddTaskBottomSheet()
                },
                onSave = {
                    calendarViewModel.saveTask()
                    calendarViewModel.closeAddTaskBottomSheet()
                }
            )
        }
    }
}

@Composable
fun MonthlyCalendarHeader(
    selectedDate: LocalDate,
    dailyTasksList: List<DailyTasks>,
    onDateSelected: (LocalDate) -> Unit
) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(12) }
    val endMonth = remember { currentMonth.plusMonths(12) }
    val daysOfWeek = remember { daysOfWeek() }

    val calendarState = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Month and Year Header
            Text(
                text = currentMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault()) +
                        " ${currentMonth.year}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Day of Week Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                daysOfWeek.forEach { day ->
                    Text(
                        text = day.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                        color = Color.Gray,
                        modifier = Modifier.width(40.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }

            // Calendar View
            HorizontalCalendar(
                state = calendarState,
                dayContent = { day ->
                    val isCurrentMonth = day.date.monthValue == currentMonth.monthValue
                    val hasTodos = dailyTasksList.any { it.date == day.date && it.tasks.isNotEmpty() }

                    MonthlyCalendarDayCell(
                        day = day,
                        isSelected = day.date == selectedDate,
                        isCurrentMonth = isCurrentMonth,
                        hasTodos = hasTodos,
                        onDayClicked = {
                            if (isCurrentMonth) onDateSelected(day.date)
                        }
                    )
                }
            )
        }
    }
}

@Composable
fun MonthlyCalendarDayCell(
    day: CalendarDay,
    isSelected: Boolean,
    isCurrentMonth: Boolean,
    hasTodos: Boolean,
    onDayClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(
                color = if (isSelected) Color(0xFF3366FF) else Color.Transparent
            )
            .clickable { onDayClicked() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = day.date.dayOfMonth.toString(),
                color = when {
                    !isCurrentMonth -> Color.LightGray
                    isSelected -> Color.White
                    day.date == LocalDate.now() -> Color(0xFF3366FF)
                    else -> Color.Black
                },
                fontWeight = if (day.date == LocalDate.now()) FontWeight.Bold else FontWeight.Normal
            )

            // Hiển thị chấm cho các ngày có công việc
            if (hasTodos && isCurrentMonth) {
                Spacer(modifier = Modifier.height(2.dp))
                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .background(Color(0xFF3366FF), shape = CircleShape)
                )
            }
        }
    }
}

@Composable
fun TaskListForSelectedDate(
    selectedDate: LocalDate,
    dailyTasksList: List<DailyTasks>,
    onTaskToggle: (Task, LocalDate) -> Unit,
    onAddButtonClick: () -> Unit,
    onTaskClick: (Task) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Danh sách công việc",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                IconButton(
                    onClick = { onAddButtonClick() },
                    modifier = Modifier
                        .background(Color(0xFF3366FF), shape = CircleShape)
                        .size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Thêm công việc",
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Task List
            val tasksForSelectedDate = dailyTasksList
                .find { it.date == selectedDate }
                ?.tasks
                ?: emptyList()

            if (tasksForSelectedDate.isEmpty()) {
                Text(
                    text = "Không có công việc nào cho ngày này",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
            } else {
                LazyColumn {
                    items(tasksForSelectedDate) { task ->
                        TaskItemView(
                            task = task,
                            onToggle = { onTaskToggle(task, selectedDate) },
                            onClick = { onTaskClick(task) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TaskItemView(
    task: Task,
    onToggle: () -> Unit,
    onClick: () -> Unit,
) {

    val isCompleted = task.status == Status.DONE

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isCompleted,
            onCheckedChange = { onToggle() },
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF3366FF),
                uncheckedColor = Color.Gray
            )
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.bodyLarge,
                textDecoration = if (isCompleted)
                    TextDecoration.LineThrough
                else
                    null,
                color = if (isCompleted) Color.Gray else Color.Black
            )

            //Show time
            task.startTime?.let { startTime ->
                val timeText = if (!task.endTime.isNullOrBlank()) {
                    "${formatTime(startTime)} - ${formatTime(task.endTime)}"
                } else {
                    formatTime(startTime)
                }

                Text(
                    text = timeText,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF666666)
                )
            }

        }
    }
}

private fun formatTime(timeStr: String?): String {
    return try {
        timeStr?.let {
            val time = LocalTime.parse(it) // parse "11:00:00" → LocalTime
            String.format("%02d:%02d", time.hour, time.minute)
        } ?: ""
    } catch (e: Exception) {
        "" // fallback nếu format sai
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewCalendarScreen() {
    AppAdvisorTheme {
        //MonthlyCalendarScreen()
    }
}