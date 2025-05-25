package com.example.appadvisor.ui.screen.signup

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
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.appadvisor.data.model.enums.Department
import com.example.appadvisor.data.model.enums.Role
import com.example.appadvisor.navigation.AppScreens
import com.example.appadvisor.ui.theme.AppAdvisorTheme
import java.util.Locale

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel()
) {

    val state = viewModel.uiState.collectAsState()
    
    val errors = viewModel.fieldErrors.collectAsState()

    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    var isConfirmPasswordVisible by remember {
        mutableStateOf(false)
    }

    var isMajorMenuExpanded by remember {
        mutableStateOf(false)
    }

    // 2 role
    val roles = Role.entries
    // 3 department
    val departments = Department.entries

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
            Text(text = "Sign Up form", fontSize = 50.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(36.dp))

            // Full name
            OutlinedTextField(
                value = state.value.name,
                onValueChange = viewModel::onNameChange,
                isError = errors.value.containsValue("name"),
                label = {
                    Text(text = "Full name")
                },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            viewModel.onNameChange("")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null
                        )
                    }
                },
                supportingText = {
                    errors.value["name"]?.let { Text(it, color = Color.Red) }
                },
                modifier = modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )

            // Email
            OutlinedTextField(
                value = state.value.email,
                onValueChange = viewModel::onEmailChange,
                isError = errors.value.containsValue("email"),
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
                    IconButton(
                        onClick = {
                            viewModel.onEmailChange("")
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                    }
                },
                supportingText = {
                    errors.value["email"]?.let { Text(it, color = Color.Red) }
                },
                modifier = modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )

            // Phone number
            OutlinedTextField(
                value = state.value.phoneNumber,
                onValueChange = viewModel::onPhoneNumberChange,
                isError = errors.value.containsKey("phoneNumber"),
                label = { Text("Phone Number") },
                singleLine = true,
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Phone, contentDescription = null)
                },
                modifier = modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                ),
                supportingText = {
                    errors.value["phoneNumber"]?.let { Text(it, color = Color.Red) }
                }
            )

            // Classroom
            OutlinedTextField(
                value = state.value.classroom.uppercase(),
                onValueChange = viewModel::onClassChange,
                isError = errors.value.containsKey("classroom"),
                label = { Text("Classroom") },
                singleLine = true,
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = null)
                },
                modifier = modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                supportingText = {
                    errors.value["classroom"]?.let { Text(it, color = Color.Red) }
                }
            )

            // Major
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = state.value.major,
                    onValueChange = {},
                    readOnly = true,
                    isError = errors.value.containsValue("major"),
                    label = { Text("Major") },
                    trailingIcon = {
                        IconButton(onClick = { isMajorMenuExpanded = !isMajorMenuExpanded }) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = "Select Major"
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    supportingText = {
                        errors.value["major"]?.let { Text(it, color = Color.Red) }
                    },
                    shape = MaterialTheme.shapes.medium
                )

                DropdownMenu(
                    expanded = isMajorMenuExpanded,
                    onDismissRequest = { isMajorMenuExpanded = false }
                ) {
                    listOf("CNTT", "ATTT", "DTVT").forEach { majorOption ->
                        DropdownMenuItem(
                            text = { Text(majorOption) },
                            onClick = {
                                viewModel.onMajorChange(majorOption)
                                isMajorMenuExpanded = false
                            }
                        )
                    }
                }
            }


            // Password
            OutlinedTextField(
                value = state.value.password,
                onValueChange = viewModel::onPasswordChange,
                isError = errors.value.containsValue("password"),
                label = {
                    Text(text = "Password")
                },
                placeholder = {
                    Text(text = "Enter password")
                },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_password_24),
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible }) {
                        Icon(
                            painter = painterResource(
                                id = if (isConfirmPasswordVisible) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24
                            ),
                            contentDescription = null
                        )
                    }
                },
                visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                supportingText = {
                    errors.value["password"]?.let { Text(it, color = Color.Red) }
                },
                modifier = modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                )
            )

            //Confirm password
            OutlinedTextField(
                value = state.value.confirmPassword,
                onValueChange = viewModel::onConfirmPasswordChange,
                isError = errors.value.containsValue("confirmPassword"),
                label = {
                    Text(text = "Confirm Password")
                },
                placeholder = {
                    Text(text = "Re-enter password")
                },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_password_24),
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            painter = painterResource(
                                id = if (isPasswordVisible) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24
                            ),
                            contentDescription = null
                        )
                    }
                },
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                supportingText = {
                    errors.value["confirmPassword"]?.let { Text(it, color = Color.Red) }
                },
                modifier = modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    // isSuccess : false -> true
                    viewModel.signUp()
                },
                modifier = modifier.fillMaxWidth(0.5f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Sign up", fontStyle = FontStyle.Italic, fontSize = 20.sp)
            }

            // Navigate login when isSuccess = true
            LaunchedEffect(key1 = state.value.isSuccess) {
                if (state.value.isSuccess) {
                    navController.navigate(AppScreens.Login.route)
                    viewModel.reset()
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Have you had account? ",
                    fontStyle = FontStyle.Normal
                )
                Text(
                    text = stringResource(id = R.string.move_to_login),
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable {
                        // Navigate to Login Screen
                        navController.navigate(AppScreens.Login.route)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignUpScreen() {
    AppAdvisorTheme {
        val navController = rememberNavController()
        SignUpScreen(modifier = Modifier, navController = navController)
    }
}