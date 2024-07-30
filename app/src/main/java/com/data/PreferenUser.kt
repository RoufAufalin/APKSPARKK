package com.data

import android.content.Context
import android.util.Log

internal class UserPreferences(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveUser(token: String, expiresIn : Int) {
        val edit = sharedPreferences.edit()
        edit.putString(TOKEN, token)
        edit.putLong(EXPIRES_AT, System.currentTimeMillis() + expiresIn * 1000)
        edit.putBoolean(IS_LOGIN, true)
        edit.apply()
        Log.d("token", token)
    }

    fun getUser(): String? {
        return sharedPreferences.getString(TOKEN, null)
    }

    fun isTokenExpired(): Boolean {
        val isLoggedIn = sharedPreferences.getBoolean(IS_LOGIN, false)
        val expiresAt = sharedPreferences.getLong(EXPIRES_AT, 0)
        return isLoggedIn && System.currentTimeMillis() < expiresAt
    }

    fun clearUser() {
        val edit = sharedPreferences.edit()
        edit.remove(TOKEN)
        edit.putBoolean(IS_LOGIN, false)
        edit.remove("expires_in")
        edit.apply()
    }

    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val TOKEN = "token"
        private const val EXPIRES_AT = "expires_at"
        private const val IS_LOGIN = "isLogin"
    }
}