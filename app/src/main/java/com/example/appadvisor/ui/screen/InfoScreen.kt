package com.example.appadvisor.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appadvisor.R


@Composable
fun InfoScreen() {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()

            ) {
                Image(
                    painter = painterResource(id = R.drawable.info_24px),
                    contentDescription = null,
                    modifier = Modifier.size(64.dp).fillMaxHeight()
                )

                Column {
                    // Name
                    Text(
                        text = "Đinh Quang Huy",
                        color = Color.Black,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(4.dp)
                    )
                    // Student ID
                    Text(
                        text = "Mã SV: CT050225",
                        color = Color.Black,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier.padding(4.dp)
                    )
                    // Major
                    Text(
                        text = "Chuyên ngành: Công nghệ thông tin",
                        color = Color.Red,
                        modifier = Modifier.padding(4.dp)
                    )

                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Ngày sinh: 02/12/2002")
        Text(text = "Email liên kết: ct050225@actvn.edu.vn")
        Text(text = "Số điện thoại: 0845899688")



    }
}

@Preview(showBackground = true)
@Composable
fun PreviewInfoScreen() {
    InfoScreen()
}
