/*
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appadvisor.data.model.PersonInfo
import com.example.appadvisor.data.model.Role
import com.example.appadvisor.ui.theme.AppAdvisorTheme

@Composable
fun InfoScreen(
    personInfo: PersonInfo
) {
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
            // Header with profile picture
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

            // Name
            Text(
                text = personInfo.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            // Detailed information
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (personInfo.role == UserRole.STUDENT &&
                        (personInfo.batch != null || personInfo.major != null)
                    ) {
                        Text(
                            text = "Thông tin học tập",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        personInfo.major?.let {
                            InformationRow(
                                iconId = R.drawable.baseline_star_24,
                                label = "Chuyên ngành",
                                value = personInfo.major
                            )
                        }

                        personInfo.batch?.let {
                            InformationRow(
                                iconId = R.drawable.desktop_computer,
                                label = "Lớp",
                                value = personInfo.batch
                            )
                        }

                        HorizontalDivider(thickness = 2.dp)
                    } else if (personInfo.role == UserRole.ADVISOR && personInfo.department != null) {
                        // Contact information section
                        Text(
                            text = "Thông tin liên lạc",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        InformationRow(
                            iconId = R.drawable.baseline_star_24,
                            label = "Khoa",
                            value = personInfo.department
                        )

                        HorizontalDivider(thickness = 2.dp)
                    }

                    InformationRow(
                        iconId = R.drawable.calendar,
                        label = "Ngày sinh",
                        value = personInfo.birthday
                    )
                    InformationRow(
                        iconId = R.drawable.baseline_call_24,
                        label = "Số điện thoại",
                        value = personInfo.phone
                    )
                    InformationRow(
                        iconId = R.drawable.baseline_email_24,
                        label = "Email",
                        value = personInfo.email
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 48.dp), // Add padding at the bottom
                contentAlignment = Alignment.Center // Center horizontally
            ) {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth(0.5f) // Make button 50% of screen width
                        .height(48.dp) // Increase button height
                ) {
                    Text(
                        text = "Đăng xuất",
                        style = MaterialTheme.typography.titleMedium, // Increase text size
                        modifier = Modifier.padding(4.dp) // Add padding for text
                    )
                }
            }
        }
    }
}

@Composable
fun InformationRow(
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

@Preview(showBackground = true)
@Composable
fun PreviewInfoStudent() {
    val personInfo = PersonInfo(
        name = "Đinh Quang Huy",
        birthday = "02/12/2002",
        phone = "0845899688",
        role = UserRole.STUDENT,
        email = "ct050225@actvn.edu.vn",
        major = "Công nghệ thông tin",
        batch = "CT5B"
    )
    AppAdvisorTheme {
        InfoScreen(personInfo = personInfo)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewInfoAdvisor() {
    AppAdvisorTheme {
        InfoScreen(
            personInfo = PersonInfo(
                name = "Nguyễn Văn A",
                birthday = "10/10/1980",
                phone = "0123456789",
                role = UserRole.ADVISOR,
                email = "advisor@actvn.edu.vn",
                department = "An toàn thông tin"
            )
        )
    }
}*/
