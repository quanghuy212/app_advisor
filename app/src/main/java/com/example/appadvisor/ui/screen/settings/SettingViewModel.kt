package com.example.appadvisor.ui.screen.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appadvisor.data.local.ThemePreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val _darkMode = MutableStateFlow(false)
    val darkMode: StateFlow<Boolean> = _darkMode

    private val _languageCode = MutableStateFlow("vi")
    val languageCode: StateFlow<String> = _languageCode

    private val _needRestart = MutableSharedFlow<Unit>()
    val needRestart = _needRestart.asSharedFlow()

    init {
        viewModelScope.launch {
            dataStore.data.collect { preferences ->
                _darkMode.value = preferences[ThemePreference.DARK_MODE_KEY] ?: false
                _languageCode.value = preferences[ThemePreference.LANGUAGE_KEY] ?: "vi"
            }
        }
    }

    fun setDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[ThemePreference.DARK_MODE_KEY] = enabled
            }
        }
    }

    fun setLanguage(language: String) {
        viewModelScope.launch {
            dataStore.edit {
                it[ThemePreference.LANGUAGE_KEY] = language
            }

            // Đợi cho đến khi flow cập nhật xong
            dataStore.data
                .map { it[ThemePreference.LANGUAGE_KEY] }
                .filterNotNull()
                .distinctUntilChanged()
                .filter { it == language }
                .first()

            _needRestart.emit(Unit)
        }
    }

}

