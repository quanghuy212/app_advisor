import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.appadvisor.R
import com.example.appadvisor.data.FeatureCard
import com.example.appadvisor.data.featureCards
import com.example.appadvisor.ui.screen.calendar.WeeklyCalendarTodoView





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
     navController: NavController
) {
    var isCalendarShown by remember {
        mutableStateOf(false)
    }
    var isLightTheme by remember {
        mutableStateOf(true)
    }

    val featureCards = featureCards.map { card ->
        FeatureCard(
            title = card.title,
            iconId = card.iconId,
            description = card.description
        )
    }

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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f)) // Đẩy IconButton sang phải

            IconButton(
                onClick = {
                    isCalendarShown = !isCalendarShown
                },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    painter = painterResource(id = if (isCalendarShown) R.drawable.calendar else R.drawable.un_calendar),
                    contentDescription = null,
                    modifier = Modifier.size(36.dp)
                )
            }
        }


        AnimatedVisibility(visible = isCalendarShown, enter = fadeIn(), exit = fadeOut()) {
            WeeklyCalendarTodoView()
        }

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
                items(featureCards) { card ->
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
                                painter = painterResource(id = card.iconId) ,
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