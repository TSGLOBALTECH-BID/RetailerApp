// data/manager/TokenManager.kt
package com.lolakashmir.retailerapp.data.manager

import android.content.Context
import javax.inject.Inject
import androidx.core.content.edit

class TokenManager @Inject constructor(
    private val context: Context
) {
    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        prefs.edit { putString(KEY_TOKEN, token) }
    }

    fun getToken(): String? {
        return prefs.getString(KEY_TOKEN, null)
    }

    fun clearToken() {
        prefs.edit { remove(KEY_TOKEN) }
    }

    companion object {
        private const val KEY_TOKEN = "auth_token"
    }
}