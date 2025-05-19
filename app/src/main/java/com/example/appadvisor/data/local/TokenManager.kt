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
    suspend fun saveAuthInfo(token: String, role: String) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
            prefs[ROLE_KEY] = role
        }
    }

    // Xóa tất cả thông tin xác thực
    suspend fun clearAuthInfo() {
        context.dataStore.edit { prefs ->
            prefs.remove(TOKEN_KEY)
            prefs.remove(ROLE_KEY)
        }
    }

    // Kiểm tra xem người dùng có vai trò cụ thể hay không
    suspend fun hasRole(role: String): Boolean {
        return getRole() == role
    }
}