package com.example.appadvisor.ui.screen.login

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.appadvisor.R
import com.example.appadvisor.navigation.AppScreens
import com.example.appadvisor.ui.theme.AppAdvisorTheme

/*
This file contains login form ui
*/

@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    val state = loginViewModel.uiState.collectAsState()

    val errors = loginViewModel.fieldErrors.collectAsState()

    LaunchedEffect(key1 = state.value.isSuccess) {
        if(state.value.isSuccess) {
            navController.navigate("home") {
                popUpTo("login") {
                    inclusive = true
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(text = "Login form", fontSize = 50.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(50.dp))

            OutlinedTextField(
                value = state.value.email,
                onValueChange = loginViewModel::onEmailChange,
                label = {
                    Text(text = "Email")
                },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { loginViewModel.onEmailChange("") }) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                    }
                },
                supportingText = {
                    errors.value["emailOrId"]?.let { Text(it, color = Color.Red) }
                },
                modifier = modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )


            OutlinedTextField(
                value = state.value.password,
                onValueChange = loginViewModel::onPasswordChange,
                label = {
                    Text(text = "Password")
                },
                placeholder = {
                    Text(text = "Enter password")
                },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { loginViewModel.onIsPasswordVisibleChange(!state.value.isPasswordVisible) }) {
                        Icon(
                            painter = painterResource(
                                id = if (state.value.isPasswordVisible) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24
                            ),
                            contentDescription = null
                        )
                    }
                },
                supportingText = {
                    errors.value["password"]?.let { Text(it, color = Color.Red) }
                },
                visualTransformation = if (state.value.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(id = R.string.forgot_password),
                fontStyle = FontStyle.Italic,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable {
                        // Process flow forgot pass
                        navController.navigate(AppScreens.ForgotPassword.route)
                    }
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    // Navigate to Home Screen
                    loginViewModel.validate()
                    Log.d("Login Screen","Validate true")
                    loginViewModel.login()
                    Log.d("Login Screen","Login successfully")

                },
                modifier = modifier.fillMaxWidth(0.5f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Login", fontStyle = FontStyle.Italic, fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Don't have an account? ",
                    fontStyle = FontStyle.Normal
                )
                Text(
                    text = stringResource(id = R.string.move_to_signup),
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable {
                        // Navigate to Sign Up Screen
                        navController.navigate("signup")
                        loginViewModel.reset()
                    }
                )
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    AppAdvisorTheme {
        val navController = rememberNavController()
        LoginScreen(navController)
    }
}
