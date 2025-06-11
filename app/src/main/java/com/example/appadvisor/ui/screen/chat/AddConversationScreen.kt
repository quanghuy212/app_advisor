package com.example.appadvisor.ui.screen.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.appadvisor.ui.screen.appointment.MultiSelectStudentDialog
import com.example.appadvisor.ui.theme.AppAdvisorTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddConversationScreen(
    navController: NavController,
    viewModel: ChatViewModel = hiltViewModel(),
) {

    val uiState by viewModel.addUiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadListStudentsByAdvisor()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tạo cuộc hội thoại") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
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
                value = uiState.groupName,
                onValueChange = viewModel::onGroupNameChange,
                label = { Text("Tên nhóm") },
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState.selectedParticipants.size >= 2
            )

            if (uiState.selectedParticipants.size < 2) {
                Text(
                    text = "* Chọn ít nhất 2 sinh viên để đặt tên nhóm!",
                    fontStyle = FontStyle.Italic,
                    color = Color.Red
                )
            }

            Text("Người tham gia đã chọn:")

            uiState.selectedParticipants.forEach {
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
                onClick = { viewModel.openSelectStudentDialog() }
            ) {
                Text("Thêm")
            }

            Button(
                onClick = {
                    viewModel.saveConversation()
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth(0.5f)
                    .align(Alignment.CenterHorizontally),
            ) {
                Text("Lưu")
            }

            if (uiState.showStudentDialog) {
                MultiSelectStudentDialog(
                    students = uiState.students,
                    initiallySelected = uiState.selectedParticipants,
                    onDismiss = { viewModel.closeSelectStudentDialog() },
                    onConfirm = { viewModel.onParticipantsChange(it) }
                )
            }
        }
    }
}
