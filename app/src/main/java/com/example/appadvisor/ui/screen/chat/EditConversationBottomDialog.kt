package com.example.appadvisor.ui.screen.chat


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appadvisor.ui.screen.appointment.MultiSelectStudentDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditConversationBottomDialog(
    viewModel: ChatViewModel = hiltViewModel(),
    onDismissRequest: () -> Unit,
    onSave: () -> Unit
) {
    val uiState by viewModel.editUiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadListStudentsByAdvisor()
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
            Text("Chỉnh sửa hội thoại", style = MaterialTheme.typography.titleLarge)

            OutlinedTextField(
                value = uiState.groupName,
                onValueChange = viewModel::onEditGroupNameChange,
                label = { Text("Tên nhóm") },
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState.selectedParticipants.size > 2
            )

            if (uiState.selectedParticipants.size <= 2) {
                Text(
                    text = "* Chọn ít nhất 2 sinh viên để đặt tên nhóm!",
                    fontStyle = FontStyle.Italic,
                    color = Color.Red
                )
            }

            Text("Người tham gia đã chọn:")
            uiState.selectedParticipants.forEach {
                Text("\t\t\t\t\t${it.name} \t-\t ${it.id.uppercase()}")
            }

            Button(onClick = { viewModel.openEditSelectStudentDialog() }) {
                Text("Chỉnh sửa người tham gia")
            }

            Button(
                onClick = {
                    // Gọi update nếu có
                    onSave()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text("Chỉnh sửa")
            }

            if (uiState.showStudentDialog) {
                MultiSelectStudentDialog(
                    students = uiState.students,
                    initiallySelected = uiState.selectedParticipants,
                    onDismiss = { viewModel.closeEditSelectStudentDialog() },
                    onConfirm = { viewModel.onEditParticipantsChange(it) }
                )
            }
        }
    }
}
