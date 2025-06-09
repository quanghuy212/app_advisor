package com.example.appadvisor.ui.screen.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appadvisor.ui.theme.AppAdvisorTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewConversationScreen(

) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tạo cuộc hội thoại") },
                navigationIcon = {
                    IconButton(
                        onClick = {}
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
                value = "",
                onValueChange = {},
                label = { Text("Tên nhóm") },
                modifier = Modifier.fillMaxWidth()
            )

/*            Text("Người tham gia đã chọn:")
            addUiState.selectedParticipants.forEach {
                Text("\t\t\t\t\t${it.name} \t-\t ${it.id.uppercase()}")
            }

            Button(
                onClick = { viewModel.openSelectStudentsDialog()}
            ) {
                Text("Thêm")
            }*/
        }
    }
}

@Preview
@Composable
fun PreviewAddConversation() {
    AppAdvisorTheme {
        AddNewConversationScreen()
    }
}