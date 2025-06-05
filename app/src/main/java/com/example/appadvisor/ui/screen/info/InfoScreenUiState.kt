package com.example.appadvisor.ui.screen.info

import com.example.appadvisor.data.model.response.ProfileResponse

data class InfoScreenUiState(
    val profile: ProfileResponse? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false
)

