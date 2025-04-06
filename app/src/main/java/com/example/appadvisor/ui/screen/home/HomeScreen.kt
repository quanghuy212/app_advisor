import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.appadvisor.data.model.UserRole
import com.example.appadvisor.ui.screen.home.HomeAdvisorScreen
import com.example.appadvisor.ui.screen.home.HomeStudentScreen

@Composable
fun HomeScreen(
    navController: NavController,
    role: UserRole
) {
    when(role) {
        UserRole.STUDENT -> HomeStudentScreen(navController = navController)
        UserRole.ADVISOR -> HomeAdvisorScreen(navController = navController)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    val navController = rememberNavController()
    HomeScreen(navController, role = UserRole.ADVISOR)
}