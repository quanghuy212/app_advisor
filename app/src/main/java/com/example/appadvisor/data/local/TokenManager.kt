package com.example.appadvisor.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_prefs")

class TokenManager(private val context: Context) {

    companion object {
        private val TOKEN_KEY = stringPreferencesKey(name = "jwt_token")
        private val ROLE_KEY = stringPreferencesKey(name = "user_role")
        private val NAME_KEY = stringPreferencesKey(name = "name")
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
        }
    }

    suspend fun getToken(): String? {
        return context.dataStore.data
        .map { prefs -> prefs[TOKEN_KEY] }
        .first()
    }

    suspend fun clearToken() {
        context.dataStore.edit { prefs ->
            prefs.remove(TOKEN_KEY)
        }
    }

    suspend fun saveName(name: String) {
        context.dataStore.edit { prefs ->
            prefs[NAME_KEY] = name
        }
    }

    suspend fun getName(): String? {
        return context.dataStore.data
            .map { prefs -> prefs[NAME_KEY] }
            .first()
    }

    // Thêm các phương thức để quản lý role
    suspend fun saveRole(role: String) {
        context.dataStore.edit { prefs ->
            prefs[ROLE_KEY] = role
        }
    }

    suspend fun getRole(): String? {
        return context.dataStore.data
            .map { prefs -> prefs[ROLE_KEY] }
            .first()
    }

    suspend fun clearRole() {
        context.dataStore.edit { prefs ->
            prefs.remove(ROLE_KEY)
        }
    }

    // Lưu cả token và role cùng một lúc
    suspend fun saveAuthInfo(token: String, role: String, name: String) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
            prefs[ROLE_KEY] = role
            prefs[NAME_KEY] = name
        }
    }

    // Xóa tất cả thông tin xác thực
    suspend fun clearAuthInfo() {
        context.dataStore.edit { prefs ->
            prefs.remove(TOKEN_KEY)
            prefs.remove(ROLE_KEY)
            prefs.remove(NAME_KEY)
        }
    }

    // Kiểm tra xem người dùng có vai trò cụ thể hay không
    suspend fun hasRole(role: String): Boolean {
        return getRole() == role
    }
}