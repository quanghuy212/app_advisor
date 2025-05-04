package com.example.appadvisor.ui.screen.signup

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appadvisor.data.model.Department
import com.example.appadvisor.data.model.Role
import com.example.appadvisor.data.model.request.SignUpRequest
import com.example.appadvisor.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    // Ui state
    private var _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState

    // Error state
    private val _fieldErrors = MutableStateFlow<Map<String, String>>(emptyMap())
    val fieldErrors: StateFlow<Map<String, String>> = _fieldErrors

    fun onEmailChange(value: String) {
        _uiState.update { it.copy(email = value) }
    }

    fun onNameChange(value: String) {
        _uiState.update { it.copy(name = value) }
    }

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value) }
    }

    fun onConfirmPasswordChange(value: String) {
        _uiState.update { it.copy(confirmPassword = value) }
    }

    fun onRoleChange(value: Role) {
        _uiState.update { it.copy(role = value) }
    }

    fun onDepartmentChange(value: Department) {
        _uiState.update { it.copy(department = value) }
    }

    fun signUp() {
        if (!validate()) return
        //userSignUp(role)

        val state = uiState.value

        val user = if (state.role == Role.STUDENT) {
            // Student
            SignUpRequest(
                email = state.email,
                name = state.name,
                role = state.role,
                password = state.password,
                department = null
            )
        } else {
            // Advisor
            SignUpRequest(
                email = state.email,
                name = state.name,
                role = state.role,
                password = state.password,
                department = state.department
            )
        }

        viewModelScope.launch {
            try {
                val result = userRepository.signUp(user)

                result.fold(
                    onSuccess = {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            isSuccess = true
                        )
                    },
                    onFailure = {
                        Log.e("SignUp ViewModel",it.message.toString())
                        _uiState.value = _uiState.value.copy(
                            isSuccess = false,
                            isLoading = false
                        )
                    }
                )

            } catch (e: Exception) {
                Log.e("Error SignUp Viewmodel",e.message.toString())
            }
        }
    }

    fun reset() {
        _uiState.value = SignUpUiState()
        Log.d("SignUpViewModel", "resetState: ${_uiState.value}")
    }

    private fun validate(): Boolean {
        val state = uiState.value
        val errors = mutableMapOf<String, String>()

        // Name validate
        if (!state.name.matches(Regex("^[A-Za-zÀ-ỹà-ỹ\\s]+\$"))) {
            errors["name"] = "Tên không chứa kí tự lạ"
        }

        // Email validate
        if (!Patterns.EMAIL_ADDRESS.matcher(state.email).matches()) {
            errors["email"] = "Email không hợp lệ"
        }

        // Password validate
        if (state.password.length < 6) {
            errors["password"] = "Độ dài mật khẩu ít nhất 6 kí tự"
        }

        // Confirm Password validate
        if (state.confirmPassword != state.password) {
            errors["confirmPassword"] = "Mật khẩu không khớp"
        }

        _fieldErrors.value = errors

        return errors.isEmpty()
    }


}