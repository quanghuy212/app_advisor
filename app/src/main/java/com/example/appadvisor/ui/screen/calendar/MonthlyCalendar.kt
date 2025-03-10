package com.example.appadvisor.ui.screen.calendar

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appadvisor.data.DateTodos
import com.example.appadvisor.data.TodoItem
import com.example.appadvisor.ui.theme.AppAdvisorTheme
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.daysOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarTodoScreen() {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var dateTodosList by remember {
        mutableStateOf(generateSampleTodos())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F4F8))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // Calendar Section
        CalendarSection(
            selectedDate = selectedDate,
            dateTodosList = dateTodosList,
            onDateSelected = { selectedDate = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Todo Section
        TodoSection(
            selectedDate = selectedDate,
            dateTodosList = dateTodosList,
            onTodoToggle = { todo, date ->
                dateTodosList = dateTodosList.map {
                    if (it.date == date) {
                        it.copy(todos = it.todos.map {
                            if (it.id == todo.id) todo.copy(isCompleted = !todo.isCompleted)
                            else it
                        })
                    } else it
                }
            }
        )
    }
}

@Composable
fun CalendarSection(
    selectedDate: LocalDate,
    dateTodosList: List<DateTodos>,
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
                    val hasTodos = dateTodosList.any { it.date == day.date && it.todos.isNotEmpty() }

                    CalendarDayItem(
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
fun CalendarDayItem(
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
fun TodoSection(
    selectedDate: LocalDate,
    dateTodosList: List<DateTodos>,
    onTodoToggle: (TodoItem, LocalDate) -> Unit
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
                    onClick = { /* Thêm công việc mới */ },
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

            // Todo List
            val todosForSelectedDate = dateTodosList
                .find { it.date == selectedDate }
                ?.todos
                ?: emptyList()

            if (todosForSelectedDate.isEmpty()) {
                Text(
                    text = "Không có công việc nào cho ngày này",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    color = Color.Gray
                )
            } else {
                LazyColumn {
                    items(todosForSelectedDate) { todo ->
                        TodoItemView(
                            todo = todo,
                            onToggle = { onTodoToggle(todo, selectedDate) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TodoItemView(
    todo: TodoItem,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = todo.isCompleted,
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
                text = todo.title,
                style = MaterialTheme.typography.bodyLarge,
                textDecoration = if (todo.isCompleted)
                    androidx.compose.ui.text.style.TextDecoration.LineThrough
                else null,
                color = if (todo.isCompleted) Color.Gray else Color.Black
            )
            Text(
                text = todo.time,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}

// Hàm sinh dữ liệu mẫu
fun generateSampleTodos(): List<DateTodos> = listOf(
    DateTodos(
        date = LocalDate.now(),
        todos = listOf(
            TodoItem(1, "Họp nhóm", "09:00 AM"),
            TodoItem(2, "Đi siêu thị", "02:00 PM"),
            TodoItem(3, "Tập thể dục", "05:30 PM")
        )
    ),
    DateTodos(
        date = LocalDate.now().plusDays(1),
        todos = listOf(
            TodoItem(4, "Học Kotlin", "10:00 AM"),
            TodoItem(5, "Đọc sách", "08:00 PM")
        )
    )
)

@Preview(showBackground = true)
@Composable
fun PreviewCalendarScreen() {
    AppAdvisorTheme {
        CalendarTodoScreen()
    }
}