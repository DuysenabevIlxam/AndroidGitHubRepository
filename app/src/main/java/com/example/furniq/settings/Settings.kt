package com.example.furniq.settings

import android.content.Context
import android.content.SharedPreferences

class Settings (context: Context){

    companion object {
        const val TOKEN = "token"
        const val LANGUAGE = "language"
    }
    private val preferences: SharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

    /** for saving referer */
    var token: String
        set(value) = preferences.edit().putString(TOKEN, value).apply()
        get() = preferences.getString(TOKEN, "") ?: ""

    fun clearToken() {
        preferences.edit().remove(TOKEN).apply()
    }
    fun isUserLoggedIn(): Boolean {
        return token.isNotEmpty()
    }

    /** app language*/
    var language: String
        set(value) = preferences.edit().putString(LANGUAGE, value).apply()
        get() = preferences.getString(LANGUAGE, "kaa") ?: "uz"

}