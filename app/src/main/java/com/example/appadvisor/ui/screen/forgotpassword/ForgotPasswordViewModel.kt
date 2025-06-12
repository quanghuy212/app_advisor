package com.example.appadvisor.ui.screen.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appadvisor.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ForgotPasswordUiState())
    val uiState: StateFlow<ForgotPasswordUiState> = _uiState.asStateFlow()

    // --- Update Inputs ---
    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, isEmailValid = true, errorMessage = null) }
    }

    fun onOtpChange(otp: String) {
        _uiState.update { it.copy(otp = otp, errorMessage = null) }
    }

    fun onNewPasswordChange(password: String) {
        _uiState.update { it.copy(newPassword = password, errorMessage = null) }
    }

    fun onConfirmPasswordChange(confirm: String) {
        _uiState.update { it.copy(confirmPassword = confirm, errorMessage = null) }
    }

    fun dismissSnackbar() {
        _uiState.update { it.copy(errorMessage = null, successMessage = null) }
    }

    fun dismissDialogs() {
        _uiState.update { it.copy(showOtpDialog = false, showNewPasswordDialog = false) }
    }

    // --- Send OTP ---
    fun sendOtp() = viewModelScope.launch {
        if (!_uiState.value.email.contains("@")) {
            _uiState.update { it.copy(isEmailValid = false) }
            return@launch
        }

        _uiState.update { it.copy(isLoading = true) }

        authRepository.sendOtp(_uiState.value.email).fold(
            onSuccess = {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        emailSent = true,
                        showOtpDialog = true,
                        successMessage = "OTP sent to your email."
                    )
                }
            },
            onFailure = {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = it.errorMessage ?: "Failed to send OTP"
                    )
                }
            }
        )
    }

    // --- Verify OTP ---
    fun verifyOtp() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }

        authRepository.verifyOtp(
            _uiState.value.email, _uiState.value.otp
        ).fold(
            onSuccess = {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        otpVerified = true,
                        showOtpDialog = false,
                        showNewPasswordDialog = true,
                        successMessage = "OTP verified successfully"
                    )
                }
            },
            onFailure = {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = it.errorMessage ?: "OTP verification failed"
                    )
                }
            }
        )
    }

    // --- Reset Password ---
    fun resetPassword() = viewModelScope.launch {
        val state = _uiState.value

        if (state.newPassword != state.confirmPassword || state.newPassword.length < 6) {
            _uiState.update { it.copy(isPasswordValid = false) }
            return@launch
        }

        _uiState.update { it.copy(isLoading = true) }

        authRepository.resetPassword(
            state.email, state.otp, state.newPassword
        ).fold(
            onSuccess = {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        passwordReset = true,
                        showNewPasswordDialog = false,
                        successMessage = "Password reset successfully"
                    )
                }
            },
            onFailure = {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = it.errorMessage ?: "Password reset failed"
                    )
                }
            }
        )
    }

    fun fakeSendOtp() {
        _uiState.update {
            it.copy(
                showOtpDialog = true
            )
        }
    }
}

