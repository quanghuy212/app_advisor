package com.example.appadvisor.ui.screen.signup

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appadvisor.data.model.Advisor
import com.example.appadvisor.data.model.Student
import com.example.appadvisor.data.repository.AdvisorRepository
import com.example.appadvisor.data.repository.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val studentRepository: StudentRepository,
    private val advisorRepository: AdvisorRepository
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

    fun onRoleChange(value: String) {
        _uiState.update { it.copy(role = value) }
    }

    fun onDepartmentChange(value: String) {
        _uiState.update { it.copy(department = value) }
    }

    fun onClassroomChange(value: String) {
        _uiState.update { it.copy(classroom = value) }
    }

    fun signUp(role: String) {

        if (!validate()) return

        when(role) {
            "Student" -> studentSignUp()
            "Advisor" -> advisorSignUp()
            else -> {
                Log.e("SignUp ViewModel","Role invalid: $role")
            }
        }
    }

    private fun studentSignUp() {
        val state = uiState.value

        val student = Student(
            name = state.name,
            email = state.email,
            classroom = state.classroom,
            password = state.password
        )

        viewModelScope.launch {
            try {
                val result = studentRepository.addStudent(student)

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

    private fun advisorSignUp() {
        val state = uiState.value

        val advisor = Advisor(
            name = state.name,
            email = state.email,
            department = state.department,
            password = state.password
        )

        viewModelScope.launch {
            try {
                val result = advisorRepository.addAdvisor(advisor)

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

    fun validate(): Boolean {
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

        // Role validate
        if (state.role.isBlank()) {
            errors["role"] = "Vui lòng chọn vai trò"
        }

        // Classroom && Department validate
        if (state.role == "Student" && state.classroom.isBlank()) {
            errors["classroom"] = "Vui lòng nhập lớp học"
        } else if (state.role == "Advisor" && state.department.isBlank()) {
            errors["department"] = "Vui lòng chọn khoa"
        }

        _fieldErrors.value = errors

        return errors.isEmpty()
    }


}