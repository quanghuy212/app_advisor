package com.example.appadvisor.ui.screen.search

import com.example.appadvisor.data.model.response.SearchResponse

data class SearchUiState(
    val isLoading: Boolean = false,
    val allResults: List<SearchResponse> = emptyList(), // raw data
    val searchResults: List<SearchResponse> = emptyList(), // filtered
    val error: String? = null,
    val searchQuery: String = "",
    val selectedFilter: String = "All" // "All", "Student", "Advisor"
)


