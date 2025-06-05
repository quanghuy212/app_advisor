package com.example.appadvisor.ui.screen.info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appadvisor.data.local.TokenManager
import com.example.appadvisor.data.model.enums.Role
import com.example.appadvisor.data.repository.InfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val infoRepository: InfoRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(InfoScreenUiState())
    val uiState: StateFlow<InfoScreenUiState> = _uiState

    var role by mutableStateOf<Role?>(null)
        private set

    init {
        fetchProfile()
        getRole()
    }


    private fun getRole() {
        viewModelScope.launch {
            role = tokenManager.getRole()?.let { Role.valueOf(it) }
        }
    }

    private fun fetchProfile() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, isError = false)
            try {
                val profile = infoRepository.getProfile()
                _uiState.value = InfoScreenUiState(
                    profile = profile,
                    isLoading = false,
                    isSuccess = true
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, isError = true)
            }
        }
    }
}