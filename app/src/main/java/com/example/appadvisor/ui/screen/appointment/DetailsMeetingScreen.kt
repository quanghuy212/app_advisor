package com.example.appadvisor.ui.screen.appointment

import android.util.Log
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
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appadvisor.data.model.enums.MeetingStatus
import com.example.appadvisor.data.model.enums.Role
import com.example.appadvisor.ui.screen.calendar.InfoRow
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsMeetingScreen(
    meetingId: Long,
    role: Role,
    viewModel: MeetingViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val meeting = uiState.meetings.find { it.id == meetingId }
    val studentId by viewModel.studentId.collectAsState()

    val isShowEditDialog by viewModel.showEditDialog.collectAsState()

    val selfParticipantInfo = meeting?.participants?.find { it.studentId == studentId }
    val selfResponse = selfParticipantInfo?.status
    Log.d("Details Meeting Screen","Self Participant Info: $selfParticipantInfo")

    LaunchedEffect(Unit) {
        if (uiState.meetings.isEmpty()) {
            viewModel.loadMeetings()
            viewModel.getStudentId()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chi tiết cuộc hẹn") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if(role == Role.ADVISOR) {
                        IconButton(
                            onClick = {
                                viewModel.setEditMeeting(meeting!!)
                                viewModel.openEditDialog()
                            },
                            enabled = (meeting?.meetingStatus != MeetingStatus.CANCELLED)
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = "Chỉnh sửa")
                        }
                    }
                }
            )
        }
    ) { padding ->
        if (meeting != null) {
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
                        text = meeting.title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    if (meeting.description.isNotBlank()) {
                        Text(
                            text = meeting.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    InfoRow("Trạng thái", meeting.meetingStatus.name)

                    // Format date
                    // LocalDate.parse().format() -- dd/mm/yyyy
                    InfoRow("Ngày gặp", LocalDate.parse(meeting.meetingDate).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))

                    // Format time
                    //LocalTime... -- hh:mm
                    InfoRow("Giờ bắt đầu", LocalTime.parse(meeting.startTime).format(DateTimeFormatter.ofPattern("HH:mm")))
                    InfoRow("Giờ kết thúc", LocalTime.parse(meeting.endTime).format(DateTimeFormatter.ofPattern("HH:mm")))

                    Text(
                        text = "Danh sách người tham gia",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(top = 12.dp, bottom = 8.dp)
                    )

                    Column {
                        meeting.participants.forEach { participant ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                shape = RoundedCornerShape(12.dp),
                                elevation = CardDefaults.cardElevation(4.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(text = "Mã: ${participant.studentId.uppercase()}", fontWeight = FontWeight.Medium)
                                        Text(text = "Tên: ${participant.studentName}")
                                    }
                                    Text(
                                        text = participant.status.name,
                                        color = when (participant.status.name) {
                                            "ACCEPTED" -> Color(0xFF4CAF50)
                                            "DECLINED" -> Color.Red
                                            else -> Color.Gray
                                        },
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }


                    Spacer(Modifier.height(12.dp))

                    // Action of student
                    if (role == Role.STUDENT) {
                        when (selfResponse?.name ?: "PENDING") {
                            "ACCEPTED" -> {
                                Text(
                                    text = "Bạn đã chấp nhận tham gia.",
                                    color = Color.Green,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }
                            "DECLINED" -> {
                                Text(
                                    text = "Bạn đã từ chối tham gia.",
                                    color = Color.Red,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }
                            else -> {
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    // Decline
                                    Button(
                                        onClick = { viewModel.declineResponse(meetingId) },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(end = 8.dp)
                                    ) {
                                        Text("Từ chối", color = Color.White)
                                    }
                                    // Accept
                                    Button(
                                        onClick = {
                                            viewModel.acceptResponse(meetingId)
                                            Log.d("Details Meeting Screen","Meeting updated: $meeting")
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(start = 8.dp)
                                    ) {
                                        Text("Chấp nhận", color = Color.White)
                                    }
                                }
                            }
                        }
                    } else {
                        // Role advisor
                        Button(
                            onClick = {
                                viewModel.deleteMeeting(meetingId)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                            enabled = (meeting.meetingStatus == MeetingStatus.PLANNED)
                        ) {
                            Text("Xóa", color = Color.White)
                        }
                    }

                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Không tìm thấy cuộc hẹn.", color = Color.Red)
            }
        }

        if (isShowEditDialog) {
            EditMeetingBottomDialog(
                onDismissRequest = {
                    viewModel.closeEditDialog()
                },
                onSave = {
                    viewModel.updateMeeting()
                    viewModel.closeEditDialog()
                }
            )
        }
    }
}
