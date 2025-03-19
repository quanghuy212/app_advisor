import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.appadvisor.ui.screen.home.HomeAdvisorScreen
import com.example.appadvisor.ui.screen.home.HomeStudentScreen

@Composable
fun HomeScreen(navController: NavController,role: String) {
    when(role) {
        "STUDENT" -> HomeStudentScreen(navController = navController)
        "ADVISOR" -> HomeAdvisorScreen(navController = navController)
        else -> Text("Không có quyền truy cập")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    val navController = rememberNavController()
    HomeScreen(navController, role = "ADVISOR")
}