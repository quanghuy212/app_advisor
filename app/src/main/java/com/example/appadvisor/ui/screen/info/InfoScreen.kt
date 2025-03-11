package com.example.appadvisor.ui.screen.info

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.appadvisor.R
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appadvisor.ui.theme.AppAdvisorTheme

@Composable
fun InfoScreen() {
    var student by remember { mutableStateOf(
        Student(
            name = "Đinh Quang Huy",
            birthday = "02/12/2002",
            phone = "0845899688",
            major = "Công nghệ thông tin",
            batch = "CT5",
            email = "ct050225@actvn.edu.vn"
        )
    ) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header với ảnh đại diện
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(120.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            // Tên sinh viên
            Text(
                text = student.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            // Thông tin chi tiết
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    InfoRow(
                        iconId = R.drawable.calendar,
                        label = "Ngày sinh",
                        value = student.birthday
                    )
                    InfoRow(
                        iconId = R.drawable.baseline_call_24,
                        label = "Số điện thoại",
                        value = student.phone
                    )
                    InfoRow(
                        iconId = R.drawable.baseline_star_24,
                        label = "Chuyên ngành",
                        value = student.major
                    )
                    InfoRow(
                        iconId = R.drawable.desktop_computer,
                        label = "Lớp",
                        value = student.batch
                    )
                    InfoRow(
                        iconId = R.drawable.baseline_email_24,
                        label = "Email",
                        value = student.email
                    )
                }
            }
        }
    }
}


@Composable
private fun InfoRow(
    iconId: Int,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            painter = painterResource(iconId),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

data class Student(
    val name: String,
    val birthday: String,
    val phone: String,
    val major: String,
    val batch: String,
    val email: String
)

@Preview(showBackground = true)
@Composable
fun PreviewInfo() {
    AppAdvisorTheme {
        InfoScreen()
    }
}