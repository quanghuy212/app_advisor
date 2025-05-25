package com.example.appadvisor.ui.screen.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.appadvisor.data.model.DailyTasks
import com.example.appadvisor.navigation.AppScreens
import com.example.appadvisor.ui.theme.AppAdvisorTheme
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.WeekDayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun WeeklyCalendarSection(
    navController: NavController,
    calendarViewModel: CalendarViewModel = hiltViewModel(),
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
            .fillMaxWidth()
            .background(Color(0xFFF0F4F8))
            .padding(16.dp)
    ) {
        // Weekly Calendar Section
        WeeklyCalendarSection(
            selectedDate = selectedDate,
            dailyTasksList = dailyTasksList,
            onDateSelected = calendarViewModel::onDateSelected
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Todo Section
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
fun WeeklyCalendarSection(
    selectedDate: LocalDate,
    dailyTasksList: List<DailyTasks>,
    onDateSelected: (LocalDate) -> Unit
) {
    val currentWeek = remember { LocalDate.now().minusDays((LocalDate.now().dayOfWeek.value - 1).toLong()) }
    val daysOfWeek = remember { daysOfWeek() }

    val weekCalendarState = rememberWeekCalendarState(
        startDate = currentWeek
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
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Weekly Calendar View
            WeekCalendar(
                state = weekCalendarState,
                dayContent = { day ->
                    val hasTodos = dailyTasksList.any {
                        it.date == day.date && it.tasks.isNotEmpty()
                    }

                    WeeklyCalendarDayItem(
                        day = day,
                        isSelected = day.date == selectedDate,
                        hasTodos = hasTodos,
                        onDayClicked = {
                            if (day.position == WeekDayPosition.RangeDate) {
                                onDateSelected(day.date)
                            }
                        }
                    )
                }
            )
        }
    }
}

@Composable
fun WeeklyCalendarDayItem(
    day: WeekDay,
    isSelected: Boolean,
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
                    day.position != WeekDayPosition.RangeDate -> Color.LightGray
                    isSelected -> Color.White
                    day.date == LocalDate.now() -> Color(0xFF3366FF)
                    else -> Color.Black
                },
                fontWeight = if (day.date == LocalDate.now()) FontWeight.Bold else FontWeight.Normal
            )

            // Show dot for days with todos
            if (hasTodos && day.position == WeekDayPosition.RangeDate) {
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

// Reuse the existing TodoSection, TodoItemView, Task, and DailyTasks from the previous implementation
// Make sure to import these or copy them from the MonthlyCalendarScreen.kt file

@Preview(showBackground = true)
@Composable
fun PreviewWeeklyCalendar() {
    AppAdvisorTheme {
        //WeeklyCalendarSection()
    }
}