
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appadvisor.data.model.enums.Role
import com.example.appadvisor.data.model.response.ProfileResponse
import com.example.appadvisor.ui.theme.AppAdvisorTheme



@Composable
fun InfoScreen(
    viewModel: InfoViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        when {
            uiState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            uiState.isError -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Đã xảy ra lỗi khi tải thông tin.")
                }
            }

            uiState.profile != null -> {
                ProfileContent(profile = uiState.profile!!)
            }
        }
    }
}

@Composable
fun ProfileContent(profile: ProfileResponse) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        // Avatar
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
            text = profile.fullName,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 12.dp, bottom = 16.dp)
        )

        // Card with Info
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (profile.major != null) {
                    Text(
                        text = "Thông tin học tập",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    HorizontalDivider(thickness = 2.dp)

                    InformationRow(
                        iconId = R.drawable.baseline_star_24,
                        label = "Chuyên ngành",
                        value = when(profile.major) {
                                "CNTT" -> "Công nghệ thông tin"
                                "ATTT" -> "An toàn thông tin"
                                "DTVT" -> "Điện tử viễn thông"
                                else -> "Not defined"
                            }
                    )
                }

                if (profile.department != null) {
                    Text(
                        text = "Thông tin cố vấn",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    HorizontalDivider(thickness = 2.dp)

                    InformationRow(
                        iconId = R.drawable.desktop_computer,
                        label = "Khoa",
                        value = when(profile.department) {
                                "CNTT" -> "Công nghệ thông tin"
                                "ATTT" -> "An toàn thông tin"
                                "DTVT" -> "Điện tử viễn thông"
                                else -> "Not defined"
                            }
                    )
                }

                HorizontalDivider(thickness = 2.dp)

                Text(
                    text = "Thông tin liên lạc",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                InformationRow(
                    iconId = R.drawable.baseline_call_24,
                    label = "Số điện thoại",
                    value = profile.phone
                )

                InformationRow(
                    iconId = R.drawable.baseline_email_24,
                    label = "Email",
                    value = profile.email
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = { /* TODO */ },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(50.dp)
            ) {
                Text(
                    text = "Đăng xuất",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(4.dp)
                )
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp), // chỉnh nhẹ lề trái nếu muốn
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
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
    AppAdvisorTheme {

    }
}

