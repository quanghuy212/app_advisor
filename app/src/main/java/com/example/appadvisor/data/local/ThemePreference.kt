package com.example.appadvisor.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object ThemePreference {
    val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
    val LANGUAGE_KEY = stringPreferencesKey("language")
}


