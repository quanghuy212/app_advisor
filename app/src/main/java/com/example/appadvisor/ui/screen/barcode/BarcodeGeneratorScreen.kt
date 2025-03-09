package com.example.appadvisor.ui.screen.barcode

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.appadvisor.ui.theme.AppAdvisorTheme
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarcodeGeneratorScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Barcode Generator", fontSize = 30.sp) },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            BarcodeImage(data = "CT050225", height = 400, width = 600)
        }
    }
}

@Composable
fun BarcodeImage(
    data: String,
    width: Int,
    height: Int,
    modifier: Modifier = Modifier
) {
    val bitmap = generateBarcodeBitmap(data = data, width = width, height = height)

    Image(
        bitmap = bitmap.asImageBitmap(),
        contentDescription = "barcode"
    )
}

private fun generateBarcodeBitmap(data: String, width: Int, height: Int): Bitmap {

    val bitMatrix = MultiFormatWriter().encode(data, BarcodeFormat.CODE_128, width, height)
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

    for (x in 0 until width) {
        for ( y in 0 until height) {
            bitmap.setPixel(x,y,if (bitMatrix[x,y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE )
        }
    }

    return bitmap
}

@Preview(showBackground = true)
@Composable
fun PreviewBarcodeGenerator() {
    AppAdvisorTheme {
        val navController = rememberNavController()
        BarcodeGeneratorScreen(navController)
    }
}