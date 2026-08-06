package com.example.recipeapp.utils

import android.content.Context

class SharedPrefManager(context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences("RecipeAppPrefs", Context.MODE_PRIVATE)

    companion object {
        private const val IS_LOGGED_IN = "isLoggedIn"
        private const val FIRST_NAME = "firstName"
    }

    fun setLoggedIn(isLoggedIn: Boolean) {
        sharedPreferences.edit()
            .putBoolean(IS_LOGGED_IN, isLoggedIn)
            .apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false)
    }

    fun saveFirstName(firstName: String) {
        sharedPreferences.edit()
            .putString(FIRST_NAME, firstName)
            .apply()
    }

    fun getFirstName(): String? {
        return sharedPreferences.getString(FIRST_NAME, "")
    }

    fun logout() {
        sharedPreferences.edit().clear().apply()
    }
}