package com.example.appadvisor.ui.component.navigation

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.appadvisor.R

@Composable
fun BottomNavBar(
    navController: NavController
) {
    val items = listOf(
        BottomNavItem("Home", painterResource(id = R.drawable.home_24px)),
        BottomNavItem("Search", painterResource(id = R.drawable.baseline_search_24)),
        BottomNavItem("Barcode", null), // Placeholder for the center button
        BottomNavItem("Chat", painterResource(id = R.drawable.chat_24px)),
        BottomNavItem("Info", painterResource(id = R.drawable.info_24px))
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Box {
        BottomAppBar(
            containerColor = Color.White,
            tonalElevation = 8.dp
        ) {
            items.forEachIndexed { index, item ->
                if (index == 2) {
                    // Center Button
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = 20.dp),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        FloatingActionButton(
                            onClick = {
                                navController.navigate(item.route) {
                                    launchSingleTop = true
                                }
                            },
                            containerColor = Color(0xFF8E44AD),
                            modifier = Modifier.clip(CircleShape)
                        ) {
                            Icon(painter = painterResource(id = R.drawable.barcode_24dp), contentDescription = "Add")
                        }
                    }
                } else {
                    val isSelected = currentRoute == item.route
                    NavigationBarItem(
                        icon = {
                            Crossfade(targetState = isSelected) { selected ->
                                val iconColor = if (selected) Color(0xFF8E44AD) else Color.Gray
                                Icon(item.icon!!, contentDescription = item.label, tint = iconColor)
                            }

                        },
                        label = { Text(item.label) },
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
}

data class BottomNavItem(val label: String, val icon: Painter?, val route: String = label.lowercase())

@Preview
@Composable
fun PreviewBottomNavBar() {
    val navController = rememberNavController()
    BottomNavBar(navController)
}
