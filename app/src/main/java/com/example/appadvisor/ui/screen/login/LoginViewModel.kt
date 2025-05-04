package com.example.appadvisor.ui.screen.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appadvisor.data.model.request.LoginRequest
import com.example.appadvisor.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    // Ui
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    // Error state
    private val _fieldErrors = MutableStateFlow<Map<String, String>>(emptyMap())
    val fieldErrors: StateFlow<Map<String, String>> = _fieldErrors

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun onIsPasswordVisibleChange(state: Boolean) {
        _uiState.update { it.copy(isPasswordVisible = state) }
    }

     fun validate(): Boolean {
        val state = uiState.value
        val errors = mutableMapOf<String, String>()

        // Validate Email
         if (state.email.isBlank()) {
             errors["email"] = "Email không được để trống"
         }

         if (!Patterns.EMAIL_ADDRESS.matcher(state.email).matches()) {
             errors["email"] = "Không hợp lệ"
         }

        // Validate Password
        if (state.password.length < 6) {
            errors["password"] = "Độ dài mật khẩu ít nhất 6 kí tự"
        }

         _fieldErrors.value = errors

        return errors.isEmpty()
    }

    fun login() {
        if (!validate()) return

        val state = uiState.value
        val userLogin = LoginRequest(
            email = state.email,
            password = state.password
        )

        viewModelScope.launch {
            try {
                val result = userRepository.login(userLogin)

                result.fold(
                    onSuccess = {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            isSuccess = true,
                            role = it.role
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
        _uiState.value = LoginUiState()
    }
}