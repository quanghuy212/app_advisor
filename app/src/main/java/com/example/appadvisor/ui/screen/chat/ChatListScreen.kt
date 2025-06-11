package com.example.appadvisor.ui.screen.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.appadvisor.data.model.Conversation
import com.example.appadvisor.data.model.enums.Role
import com.example.appadvisor.navigation.AppScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(
    navController: NavController,
    viewModel: ChatViewModel = hiltViewModel()
) {
    var searchQuery by remember { mutableStateOf("") }

    val uiState by viewModel.uiState.collectAsState()

    val role = viewModel.role

    val showEditDialog by viewModel.showEditDialog.collectAsState()

    val showDeleteDialog by viewModel.showDeleteDialog.collectAsState()

    LaunchedEffect(true) {
        viewModel.getListConversation()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Đoạn Chat") },
            )
        },
        floatingActionButton = {
            if (role == Role.ADVISOR) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(AppScreens.AddChat.route)
                    },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Tạo đoạn chat mới",
                        tint = Color.White
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Tùy chọn: Thanh tìm kiếm
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Tìm kiếm đoạn chat...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                singleLine = true,
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null)
                }
            )

            // Danh sách đoạn chat
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.conversationList) { conversation ->
                    ConversationItem(
                        conversation = conversation,
                        onClick = { navController.navigate(AppScreens.DetailsChat.withId(id = conversation.id)) },
                        onEdit = {
                            viewModel.setEditUiState(conversation)
                            viewModel.openEditDialog()
                        },
                        onDelete = {
                            viewModel.setDeleteConversation(conversation)
                            viewModel.openDeleteDialog()
                        },
                        showActions = role == Role.ADVISOR
                    )
                }
            }

            if (showEditDialog) {
                EditConversationBottomDialog(
                    onDismissRequest = { viewModel.closeEditDialog() },
                    onSave = {
                        viewModel.updateConversation()
                        viewModel.closeEditDialog()
                    }
                )
            }

            if (showDeleteDialog) {
                AlertDialog(
                    onDismissRequest = { viewModel.closeDeleteDialog() },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                viewModel.deleteConversation()
                                viewModel.closeDeleteDialog()
                            }) {
                            Text("Xóa", color = Color.Red)
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { viewModel.closeDeleteDialog() }
                        ) {
                            Text("Hủy")
                        }
                    },
                    title = { Text("Xác nhận xóa") },
                    text = { Text("Bạn có chắc muốn xóa trò chuyện này không?") }
                )
            }
        }
    }
}

@Composable
fun ConversationItem(
    conversation: Conversation,
    onClick: () -> Unit,
    onEdit: (Conversation) -> Unit,
    onDelete: (Conversation) -> Unit,
    showActions: Boolean = true
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = conversation.name.first().toString(),
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Thông tin đoạn chat
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = conversation.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                }
            }

            // Menu icon (chỉ hiển thị nếu showActions = true)
            if (showActions) {
                IconButton(
                    onClick = { expanded = true }
                ) {
                    Icon(
                        Icons.Default.MoreVert,
                        contentDescription = "Menu",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Dropdown Menu
        if (showActions) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                offset = DpOffset(x = (-8).dp, y = (-16).dp)
            ) {
                if (conversation.isGroup) {
                    DropdownMenuItem(
                        text = { Text("Chỉnh sửa") },
                        onClick = {
                            expanded = false
                            onEdit(conversation)
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = "Chỉnh sửa"
                            )
                        }
                    )
                }

                DropdownMenuItem(
                    text = { Text("Xóa") },
                    onClick = {
                        expanded = false
                        onDelete(conversation)
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Xóa"
                        )
                    }
                )
            }
        }
    }

    HorizontalDivider(
        modifier = Modifier.padding(start = 78.dp),
        color = Color.LightGray.copy(alpha = 0.5f)
    )
}

// Preview
@Preview(showBackground = true)
@Composable
fun ChatListScreenPreview() {
}