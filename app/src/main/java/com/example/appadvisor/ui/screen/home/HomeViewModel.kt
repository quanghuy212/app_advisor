package com.example.appadvisor.ui.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appadvisor.data.local.TokenManager
import com.example.appadvisor.data.model.enums.Role
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val tokenManager: TokenManager
): ViewModel() {

    var role by mutableStateOf<Role?>(null)
        private set

    var name by mutableStateOf("")
        private set

    init {
        loadInfo()
    }

    private fun loadInfo() {
        viewModelScope.launch {
            role = tokenManager.getRole()?.let { Role.valueOf(it) }
            name = tokenManager.getName() ?: ""
        }
    }
}