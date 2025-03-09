import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.appadvisor.R


data class DemoCard(
    val title: String,
    val icon: Painter,
    val description: String
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
     navController: NavController
) {
    val isCalendarShown by remember {
        mutableStateOf(false)
    }
    var isLightTheme by remember {
        mutableStateOf(true)
    }
    val demoCards = listOf(
        DemoCard(
            "Calendar",
            painterResource(id = R.drawable.baseline_calendar_month_24),
            "Schedule your day"
        ),
        DemoCard(
            "Form",
            painterResource(id = R.drawable.description),
            "Manage your tasks"
        ),
        DemoCard(
            "Results",
            painterResource(id = R.drawable.target_8992467),
            "Write your thoughts"
        ),
        DemoCard(
            "Settings",
            painterResource(id = R.drawable.settings_24px),
            "Customize app"
        )
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(48.dp)
                    .clip(shape = CircleShape)
            )
            Column {
                Text(
                    text = "Đinh Quang Huy | CT050225",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                Text(text = "Khoa công nghệ thông tin", fontStyle = FontStyle.Italic)
            }

            // Move Icon button to end of row
            Spacer(modifier =  Modifier.weight(1f))

            IconButton(
                onClick = {
                    isLightTheme = !isLightTheme
                },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    painter = if(isLightTheme) painterResource(id = R.drawable.light_mode_24dp)
                        else painterResource(id = R.drawable.dark_mode_24dp),
                    contentDescription = null,
                    modifier = Modifier.size(36.dp)
                )
            }
        }
        // Box calendar

            Icon(
                painter = painterResource(id =
                    if (isCalendarShown) R.drawable.baseline_calendar_month_24 else R.drawable.un_calendar_month
                ),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )

        // Main content
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(demoCards) { card ->
                    Card(
                        modifier = Modifier
                            .aspectRatio(1.3f)
                            .fillMaxWidth()
                            .clickable {
                                when (card.title) {
                                    "Calendar" -> navController.navigate("calendar")
                                    "Form" -> navController.navigate("form")
                                    "Results" -> navController.navigate("results")
                                    "Settings" -> navController.navigate("settings")
                                }
                            },
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        shape = MaterialTheme.shapes.extraLarge
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = card.icon,
                                contentDescription = card.title,
                                modifier = Modifier.size(36.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = card.title,
                                style = MaterialTheme.typography.titleMedium,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = card.description,
                                style = MaterialTheme.typography.bodySmall,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    val navController = rememberNavController()
    HomeScreen(navController)
}