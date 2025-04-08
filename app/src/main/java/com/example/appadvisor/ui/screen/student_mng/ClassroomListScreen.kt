package com.example.appadvisor.ui.screen.student_mng

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.appadvisor.ui.theme.AppAdvisorTheme


@Composable
fun ClassListScreen(navController: NavController) {
    val classes = listOf("CT5A", "CT5B", "CT5C")

    LazyColumn {
        items(classes) { classId ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        navController.navigate("students/$classId")
                    }
            ) {
                Text(text = classId, modifier = Modifier.padding(16.dp), fontWeight = FontWeight.Bold)
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewClassListScreen() {
    AppAdvisorTheme {
        val navController = rememberNavController()
        ClassListScreen(navController)
    }
}