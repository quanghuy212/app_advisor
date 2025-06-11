package com.example.appadvisor.ui.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appadvisor.data.model.response.SearchResponse
import com.example.appadvisor.data.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState

    fun updateSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }

        filterAndEmitResults()
    }

    fun updateFilter(filter: String) {
        _uiState.update { it.copy(selectedFilter = filter) }

        filterAndEmitResults()
    }

    fun clearSearch() {
        _uiState.update { it.copy(searchQuery = "") }
        filterAndEmitResults()
    }

    fun retry() {
        fetchData()
    }

    init {
        fetchData()
    }

    private fun fetchData() {
        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            val result = searchRepository.searchQuery("") // chỉ gọi 1 lần
            result.fold(
                onSuccess = {
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            allResults = it,
                            error = null
                        )
                    }
                    filterAndEmitResults()
                },
                onFailure = {
                    _uiState.update {
                        it.copy(isLoading = false, error = it.error.toString())
                    }
                }
            )
        }
    }

    private fun filterAndEmitResults() {
        val currentState = _uiState.value
        val query = currentState.searchQuery.trim().lowercase()
        val filter = currentState.selectedFilter

        val filtered = currentState.allResults.filter { item ->
            val matchQuery = query.isBlank() || listOf(
                item.name, item.email, item.id
            ).any { it.contains(query, ignoreCase = true) }

            val matchFilter = when (filter) {
                "Student" -> item.role == "STUDENT"
                "Advisor" -> item.role == "ADVISOR"
                else -> true
            }

            matchQuery && matchFilter
        }

        _uiState.update { it.copy(searchResults = filtered) }
    }
}

