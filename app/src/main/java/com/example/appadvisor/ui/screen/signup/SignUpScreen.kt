package com.example.appadvisor.ui.screen.signup

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
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
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
import com.example.appadvisor.ui.theme.AppAdvisorTheme

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    val state = viewModel.uiState.collectAsState()
    
    val errors = viewModel.fieldErrors.collectAsState()

    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    var isConfirmPasswordVisible by remember {
        mutableStateOf(false)
    }

    var isRoleMenuExpanded by remember {
        mutableStateOf(false)
    }

    var isDepartmentMenuExpanded by remember {
        mutableStateOf(false)
    }

    // 2 role
    val roles = listOf("Student", "Advisor")
    // 3 department
    val departments = listOf("CNTT","ATTT","DTVT")

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

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = state.value.role,
                    onValueChange = viewModel::onRoleChange,
                    isError = errors.value.containsValue("role"),
                    label = {
                        Text(text = "Role")
                    },
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = { isRoleMenuExpanded = true }
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = null
                            )
                        }
                    },
                    supportingText = {
                        errors.value["role"]?.let { Text(it, color = Color.Red) }
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

                DropdownMenu(
                    expanded = isRoleMenuExpanded,
                    onDismissRequest = { isRoleMenuExpanded = false },
                    modifier = Modifier.fillMaxWidth(0.75f)
                ) {
                    roles.forEach { roleOption ->
                        DropdownMenuItem(
                            text = {
                                Text(text = roleOption)
                            },
                            onClick = {
                                viewModel.onRoleChange(roleOption)
                                isRoleMenuExpanded = false
                            }
                        )
                    }
                }
            }

            if (state.value.role == "Advisor") {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = state.value.department,
                        onValueChange = viewModel::onDepartmentChange,
                        isError = errors.value.containsValue("department"),
                        label = {
                            Text(text = "Department")
                        },
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = null
                            )
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = { isDepartmentMenuExpanded = true }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = null
                                )
                            }
                        },
                        supportingText = {
                            errors.value["department"]?.let { Text(it, color = Color.Red) }
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

                    DropdownMenu(
                        expanded = isDepartmentMenuExpanded,
                        onDismissRequest = { isDepartmentMenuExpanded = false },
                        modifier = Modifier.fillMaxWidth(0.75f)
                    ) {
                        departments.forEach { roleOption ->
                            DropdownMenuItem(
                                text = {
                                    Text(text = roleOption)
                                },
                                onClick = {
                                    viewModel.onDepartmentChange(value = roleOption)
                                    isDepartmentMenuExpanded = false
                                }
                            )
                        }
                    }
                }
            } else {
                OutlinedTextField(
                    value = state.value.classroom,
                    onValueChange = viewModel::onClassroomChange,
                    isError = errors.value.containsValue("classroom"),
                    label = {
                        Text(text = "Classroom")
                    },
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.teach),
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { viewModel.onClassroomChange("") }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                        }
                    },
                    supportingText = {
                        errors.value["classroom"]?.let { Text(it, color = Color.Red) }
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
            }

            
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
                    viewModel.signUp(role = state.value.role)
                },
                modifier = modifier.fillMaxWidth(0.5f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Sign up", fontStyle = FontStyle.Italic, fontSize = 20.sp)
            }

            // Navigate login when isSuccess = true
            LaunchedEffect(key1 = state.value.isSuccess) {
                if (state.value.isSuccess) {
                    navController.navigate("login")
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
                        navController.navigate("login")
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
        SignUpScreen(navController)
    }
}