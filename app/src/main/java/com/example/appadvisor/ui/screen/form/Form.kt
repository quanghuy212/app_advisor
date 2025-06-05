package com.example.appadvisor.ui.screen.form

import android.app.DownloadManager
import android.content.Context
import android.os.Build
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.appadvisor.R
import com.example.appadvisor.data.model.Document
import com.example.appadvisor.ui.theme.AppAdvisorTheme


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentsScreen(
    navController: NavController,
    viewmodel: FormViewModel = hiltViewModel(),
    onDocumentClick: (Document) -> Unit,
) {

    LaunchedEffect(Unit) {
        viewmodel.getDocuments()
    }

    val context = LocalContext.current

    val uiState = viewmodel.uiState.collectAsState()

    var selectedType by remember { mutableStateOf<String?>(null) }

    val documentTypes = listOf("All", "Form", "Reference", "Guide")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Văn bản", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Quay lại")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Filter theo type
            ScrollableTabRow(
                selectedTabIndex = if (selectedType == null) 0 else documentTypes.indexOf(selectedType),
                modifier = Modifier.fillMaxWidth(),
                edgePadding = 16.dp
            ) {
                documentTypes.forEachIndexed { index, type ->
                    Tab(
                        selected = (selectedType == type) || (index == 0 && selectedType == null),
                        onClick = {
                            selectedType = if (index == 0) null else type
                        },
                        text = { Text(type) }
                    )
                }
            }

            if (uiState.value.isLoading) {
                Text("Đang tải...", modifier = Modifier.padding(16.dp))
            } else {
                val filteredDocuments = if (selectedType == null) {
                    uiState.value.documents
                } else {
                    uiState.value.documents.filter { it.type.toString() == selectedType }
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(filteredDocuments) { document ->
                        DocumentItem(
                            document = document,
                            onDocumentClick = { onDocumentClick(document) },
                            onDownloadClick = {
                                if (document.downloadUrl != null) {
                                    downloadDocument(context, document)
                                } else {
                                    Toast.makeText(context, "Không có link tải xuống", Toast.LENGTH_SHORT).show()
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DocumentItem(
    document: Document,
    onDocumentClick: () -> Unit,
    onDownloadClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onDocumentClick),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // File type icon
            Icon(
                imageVector = ImageVector.vectorResource(getFileTypeIcon(document.contentType)),
                contentDescription = "File type",
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Document info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = document.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = document.type.toString(),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary
                )

                // File size info
                Text(
                    text = formatFileSize(document.fileSize),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Download button
            IconButton(onClick = onDownloadClick) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.download),
                    contentDescription = "Download",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        HorizontalDivider(modifier = Modifier.fillMaxWidth())
    }
}

// Helper functions
fun getFileTypeIcon(mimeType: String?): Int {
    return when (mimeType) {
        "application/pdf" -> R.drawable.pdf_type
        "application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document" -> R.drawable.msdoc_type
        else -> R.drawable.pdf_type
    }
}

fun formatFileSize(bytes: Long?): String {
    if (bytes == null) return "N/A"

    return when {
        bytes >= 1_000_000 -> "${String.format("%.1f", bytes / 1_000_000.0)} MB"
        bytes >= 1_000 -> "${String.format("%.1f", bytes / 1_000.0)} KB"
        else -> "$bytes B"
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun downloadDocument(context: Context, document: Document) {
    try {
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val request = DownloadManager.Request(document.downloadUrl?.toUri())
            .setTitle("Đang tải: ${document.title}")
            .setDescription("Tài liệu đang được tải xuống")
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "${document.title}.pdf"
            )
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        downloadManager.enqueue(request)
        Toast.makeText(context, "Bắt đầu tải ${document.title}", Toast.LENGTH_SHORT).show()

    } catch (e: Exception) {
        Toast.makeText(context, "Lỗi khi tải: ${e.message}", Toast.LENGTH_LONG).show()
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(showBackground = true)
@Composable
fun PreviewDocuments1Screen() {
    AppAdvisorTheme {
        val navController = rememberNavController()
        DocumentsScreen(navController, onDocumentClick = {})
    }
}