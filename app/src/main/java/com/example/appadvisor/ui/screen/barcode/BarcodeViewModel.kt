package com.example.appadvisor.ui.screen.barcode

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appadvisor.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BarcodeViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    var uiState by mutableStateOf<BarcodeUiState>(BarcodeUiState.Loading)
        private set

    init {
        getCurrentUserId()
    }

    private fun getCurrentUserId() {
        viewModelScope.launch {
            val result = userRepository.getCurrentUser()
            uiState = result.fold(
                onSuccess = { BarcodeUiState.Success(it.id) },
                onFailure = { BarcodeUiState.Error(it.message ?: "Unknown error") }
            )
        }
    }
}

sealed class BarcodeUiState {
    data object Loading : BarcodeUiState()
    data class Success(val userId: String) : BarcodeUiState()
    data class Error(val message: String) : BarcodeUiState()
}
