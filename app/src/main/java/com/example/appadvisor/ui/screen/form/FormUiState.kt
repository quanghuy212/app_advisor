package com.example.appadvisor.ui.screen.form

import com.example.appadvisor.data.model.Document

data class FormUiState(
    val documents: List<Document> = emptyList(),
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false
)