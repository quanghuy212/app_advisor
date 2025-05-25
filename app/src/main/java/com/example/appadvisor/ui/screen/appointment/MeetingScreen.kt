package com.example.appadvisor.ui.screen.appointment

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.appadvisor.data.model.Meeting
import com.example.appadvisor.data.model.enums.MeetingStatus
import com.example.appadvisor.data.model.enums.Role
import com.example.appadvisor.navigation.AppScreens
import com.example.appadvisor.ui.theme.AppAdvisorTheme
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeetingScreen(
    navController: NavController,
    meetingViewModel: MeetingViewModel = hiltViewModel(),
    onNavigateToDetail: (Meeting) -> Unit = {},
    onNavigateToCreate: () -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    val uiState by meetingViewModel.uiState.collectAsState()
    //val userRole by meetingViewModel.userRole.collectAsState()
    val filteredAppointments = meetingViewModel.getFilteredAppointments()

    val role = meetingViewModel.role

    LaunchedEffect(uiState.meetings) {
        meetingViewModel.loadMeetings()
        Log.d("AppointmentScreen", "UI received ${uiState.meetings.size} meetings")
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Meeting") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            // Only show FAB if user is advisor
            if (role == Role.ADVISOR) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(AppScreens.CreateMeeting.route)
                    },
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Create appointment")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Tab Row to filter by status
            TabRow(
                selectedTabIndex = MeetingStatus.valueOf(uiState.selectedTab).ordinal
            ) {
                MeetingStatus.entries.forEach { status ->
                    Tab(
                        selected = uiState.selectedTab == status.name,
                        onClick = { meetingViewModel.selectTab(status.name) },
                        text = {
                            Text(
                                text = status.name,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    )
                }
            }

            // Meeting list
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (filteredAppointments.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No appointments found")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredAppointments) { meeting ->
                        AppointmentItem(
                            meeting = meeting,
                            onClick = {
                                navController.navigate(AppScreens.MeetingDetails.withId(meeting.id))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AppointmentItem(
    meeting: Meeting,
    onClick: () -> Unit
) {
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    val date = LocalDate.parse(meeting.meetingDate).format(dateFormatter)
    val startTime = LocalTime.parse(meeting.startTime).format(timeFormatter)
    val endTime = LocalTime.parse(meeting.endTime).format(timeFormatter)

    Card(
        modifier = Modifier.fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.DateRange,
                contentDescription = null,
                modifier = Modifier.padding(end = 16.dp)
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = meeting.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = date,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "$startTime - $endTime",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAppointmentScreen() {
    AppAdvisorTheme {

    }
}