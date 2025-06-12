package com.example.appadvisor.ui.screen.forgotpassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch

@Composable
fun OtpDialog(
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
    onVerifySuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val otpChars = remember(uiState.otp) {
        List(6) { index -> uiState.otp.getOrNull(index)?.toString() ?: "" }
    }
    val focusRequesters = List(6) { remember { FocusRequester() } }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            scope.launch {
                snackbarHostState.showSnackbar(it)
                viewModel.dismissSnackbar()
            }
        }
    }

    if (uiState.isLoading) {
        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Nhập mã OTP",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "Mã OTP đã được gửi đến email của bạn",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Row(
                    modifier = Modifier.fillMaxWidth(), // Đảm bảo Row chiếm toàn bộ chiều rộng
                    horizontalArrangement = Arrangement.spacedBy(4.dp), // Tăng khoảng cách giữa các ô OTP
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    otpChars.forEachIndexed { index, char ->
                        OutlinedTextField(
                            value = char,
                            onValueChange = { value ->
                                if (value.length <= 1 && value.all { it.isDigit() }) {
                                    val newOtp = otpChars.toMutableList()
                                    newOtp[index] = value
                                    viewModel.onOtpChange(newOtp.joinToString(""))

                                    if (value.isNotEmpty() && index < 5) {
                                        focusRequesters[index + 1].requestFocus()
                                    }
                                }
                            },
                            modifier = Modifier
                                .width(44.dp)
                                .height(56.dp)
                                .focusRequester(focusRequesters[index]),
                            textStyle = LocalTextStyle.current.copy(
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    if (index < 5) {
                                        focusRequesters[index + 1].requestFocus()
                                    } else {
                                        // Hide keyboard if it's the last digit
                                        // keyboardController?.hide() // Requires rememberInputConnection
                                    }
                                }
                            ),
                            singleLine = true
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Hủy")
                    }

                    Button(
                        onClick = {
                            if (uiState.otp.length == 6) {
                                viewModel.verifyOtp()
                                onVerifySuccess()
                            }
                        },
                        modifier = Modifier.weight(1f),
                        enabled = uiState.otp.length == 6
                    ) {
                        Text("Xác thực")
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun OtpDialogPreview() {
    MaterialTheme {
        OtpDialog(
            onDismiss = {},
            onVerifySuccess = {}
        )
    }
}