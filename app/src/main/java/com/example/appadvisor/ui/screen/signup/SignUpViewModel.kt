package com.example.appadvisor.ui.screen.signup

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appadvisor.data.model.request.SignUpRequest
import com.example.appadvisor.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
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

    fun onPhoneNumberChange(value: String) {
        _uiState.update { it.copy(phoneNumber = value) }
    }

    fun onClassChange(value: String) {
        _uiState.update { it.copy(classroom = value) }
    }

    fun onMajorChange(value: String) {
        _uiState.update { it.copy(major = value) }
    }

    fun signUp() {

        // Validate form
        if (!validate()) return

        val state = uiState.value

        // Create Sign Up Request
        val request = SignUpRequest(
            email = state.email,
            name = state.name,
            password = state.password,
            phoneNumber = state.phoneNumber,
            major = state.major,
            classroom = state.classroom
        )


        viewModelScope.launch {
            try {
                val result = authRepository.signUp(request)

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

        // Phone validate (bắt đầu bằng 0, 10 số)
        if (!state.phoneNumber.matches(Regex("^0\\d{9}\$"))) {
            errors["phoneNumber"] = "Số điện thoại không hợp lệ"
        }

        // Classroom validate
        if (state.classroom.isBlank()) {
            errors["classroom"] = "Vui lòng nhập mã lớp"
        } else if (!state.classroom.matches(Regex("^(ct|dt|at)[0-9]{1,2}[A-Z]\$\n"))) {
            errors["classroom"] = "Mã lớp không đúng định dạng (CT5B hoặc AT20B)"
        }

        // Major validate
        if (state.major.isBlank()) {
            errors["major"] = "Vui lòng chọn chuyên ngành"
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