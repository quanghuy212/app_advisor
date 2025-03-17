package com.example.appadvisor.ui.screen.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ChatPreview(
    val id: String,
    val name: String,
    val lastMessage: String,
    val timestamp: String,
    val unreadCount: Int = 0,
    val avatarUrl: String? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(
    chatPreviews: List<ChatPreview>,
    onChatSelected: (String) -> Unit,
    onNewChatClicked: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Đoạn Chat") },
                actions = {
                    IconButton(onClick = { /* TODO: Implement search */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Tìm kiếm")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNewChatClicked,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Tạo đoạn chat mới",
                    tint = Color.White
                )
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
                items(chatPreviews) { chatPreview ->
                    ChatPreviewItem(
                        chatPreview = chatPreview,
                        onClick = { onChatSelected(chatPreview.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun ChatPreviewItem(
    chatPreview: ChatPreview,
    onClick: () -> Unit
) {
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
                text = chatPreview.name.first().toString(),
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
                    text = chatPreview.name,
                    fontWeight = if (chatPreview.unreadCount > 0) FontWeight.Bold else FontWeight.Normal,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = chatPreview.timestamp,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = chatPreview.lastMessage,
                    fontSize = 14.sp,
                    color = if (chatPreview.unreadCount > 0) Color.Black else Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                if (chatPreview.unreadCount > 0) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (chatPreview.unreadCount > 99) "99+" else chatPreview.unreadCount.toString(),
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }

    Divider(
        modifier = Modifier.padding(start = 78.dp),
        color = Color.LightGray.copy(alpha = 0.5f)
    )
}

// Preview
@Preview(showBackground = true)
@Composable
fun ChatListScreenPreview() {
    val sampleChats = listOf(
        ChatPreview(
            id = "1",
            name = "Nguyễn Văn A",
            lastMessage = "Hẹn gặp lại bạn vào ngày mai nhé!",
            timestamp = "14:30",
            unreadCount = 2
        ),
        ChatPreview(
            id = "2",
            name = "Nhóm Dự án ABC",
            lastMessage = "Trưởng nhóm: Deadline là thứ 6 tuần này",
            timestamp = "Hôm qua",
            unreadCount = 0
        ),
        ChatPreview(
            id = "3",
            name = "Lê Thị B",
            lastMessage = "Cảm ơn bạn rất nhiều!",
            timestamp = "Thứ 2",
            unreadCount = 0
        ),
        ChatPreview(
            id = "4",
            name = "Phòng Kỹ thuật",
            lastMessage = "Bạn đã gửi một file: Báo cáo kỹ thuật.pdf",
            timestamp = "23/03",
            unreadCount = 5
        )
    )

    ChatListScreen(
        chatPreviews = sampleChats,
        onChatSelected = { },
        onNewChatClicked = { }
    )
}