import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GPAProgressBar(
    gpaValue: Float,
    maxGPA: Float = 4.0f,
    canvasSize: Dp = 200.dp,
    strokeWidth: Float = 75f
) {
    val percentage = (gpaValue / maxGPA) * 100
    val sweepAngle by animateFloatAsState(
        targetValue = (2.4 * percentage).toFloat(),
        animationSpec = tween(1000)
    )

    val progressColor by animateColorAsState(
        targetValue = when {
            gpaValue >= 3.6f -> Color(0xFF4CAF50) // Green
            gpaValue >= 3.2f -> Color(0xFF2196F3) // Blue
            gpaValue >= 2.5f -> Color(0xFFFFC107) // Yellow
            gpaValue >= 2.0f -> Color(0xFFFF9800) // Orange
            else -> Color(0xFFF44336) // Red
        },
        animationSpec = tween(1000)
    )

    Column(
        modifier = Modifier
            .size(canvasSize)
            .drawBehind {
                val componentSize = size / 1.25f
                drawArc(
                    size = componentSize,
                    color = Color.Gray.copy(alpha = 0.3f), // Background
                    startAngle = 150f,
                    sweepAngle = 240f,
                    useCenter = false,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                    topLeft = Offset(
                        x = (size.width - componentSize.width) / 2f,
                        y = (size.height - componentSize.height) / 2f
                    )
                )
                drawArc(
                    size = componentSize,
                    color = progressColor, // Dynamic color
                    startAngle = 150f,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                    topLeft = Offset(
                        x = (size.width - componentSize.width) / 2f,
                        y = (size.height - componentSize.height) / 2f
                    )
                )
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "GPA",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray.copy(alpha = 0.6f),
            textAlign = TextAlign.Center
        )
        Text(
            text = "%.2f".format(gpaValue),
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = progressColor,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GPAPreview() {
    GPAProgressBar(gpaValue = 1.4f)
}