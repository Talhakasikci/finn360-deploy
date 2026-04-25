package com.talhakasikci.finn360fe.utils

import android.content.Context
import android.content.SharedPreferences

class TokenManager (context: Context) {
    private var prefs : SharedPreferences = context.getSharedPreferences("finn360_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        val editor = prefs.edit()
        editor.putString("AUTH_TOKEN", token)
        editor.apply()
    }

    fun getToken(): String? {
        return prefs.getString("AUTH_TOKEN", null)
    }

    fun clearToken() {
        val editor = prefs.edit()
        editor.remove("AUTH_TOKEN")
        editor.apply()
    }
}