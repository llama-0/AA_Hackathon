package com.hackatron52.androidacademyhackathon.data.network

import android.content.Context

class PreferencesProvider(context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences(KEY_STR_NAME_FOLDER, Context.MODE_PRIVATE)

    fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String): String? =
        sharedPreferences.getString(key, null)

    fun hasKey(key: String): Boolean {
        return sharedPreferences.contains(key)
    }

    fun clearListFeedback() {
        sharedPreferences.edit().remove(KEY_STR_FEEDBACK).apply()
    }

    //TODO add key for your data
    companion object {
        const val KEY_STR_FEEDBACK = "Feedback"
        const val KEY_STR_NAME_FOLDER = "sharedPreferFiles"
    }
}