package com.example.appadvisor.ui.screen.signup


import com.example.appadvisor.data.model.enums.Department
import com.example.appadvisor.data.model.enums.Role

data class SignUpUiState(
    var email: String = "",
    var name: String = "",
    var password: String = "",
    var confirmPassword: String = "",
    var major: String = "",
    var classroom: String = "",
    var phoneNumber: String = "",

    var isLoading: Boolean = false,
    var isSuccess: Boolean = false
)