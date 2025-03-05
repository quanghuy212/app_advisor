package com.example.appadvisor.ui.component
/*
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.appadvisor.R

data class BottomNavItem(val label: String, val icon: Painter, val route: String = label.lowercase())

@Composable
fun CustomBottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem("Home", painterResource(id = R.drawable.home_24px)),
        BottomNavItem("Search", painterResource(id = R.drawable.chat_24px)),
        BottomNavItem("History", painterResource(id = R.drawable.chat_24px)),
        BottomNavItem("Profile", painterResource(id = R.drawable.info_24px))
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp) // Chiều cao tùy chỉnh
            .background(Color.Transparent)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRoundRect(
                color = Color.White,
                cornerRadius = CornerRadius(50f, 50f),
                size = size
            )
        }
        BottomNavigation(
            modifier = Modifier
                .clip(RoundedCornerShape(size = 30.dp))
                .background(Color.White)
                .shadow(8.dp, RoundedCornerShape(30.dp)),
            backgroundColor = Color.White,
            elevation = 0.dp
        ) {
            items.forEach { item ->
                val isSelected = currentRoute == item.route
                BottomNavigationItem(
                    icon = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            Icon(
                                painter = item.icon,
                                contentDescription = item.label,
                                tint = if (currentRoute == item.route) Color(0xFF6A1B9A) else Color.Gray
                            )
                            AnimatedVisibility(
                                visible = currentRoute == item.route,
                                enter = fadeIn() + slideInHorizontally(initialOffsetX = { it / 2 }),
                                exit = fadeOut() + slideOutHorizontally(targetOffsetX = { it / 2 })
                            ) {
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = item.label,
                                    fontSize = 12.sp,
                                    color = Color(0xFF6A1B9A),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    },
                    alwaysShowLabel = false,
                    selected = isSelected,
                    onClick = {
                        navController.navigate(item.route) {
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBottomNavigationBar() {
    val navController = rememberNavController()
    CustomBottomNavigationBar(navController)
}
*/
