package com.hackatron52.androidacademyhackathon

import android.app.Application
import com.google.gson.Gson
import com.hackatron52.androidacademyhackathon.data.network.PreferencesProvider
import com.hackatron52.androidacademyhackathon.presentation.model.FeedbackModel
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    lateinit var model: FeedbackModel

    override fun onCreate() {
        super.onCreate()
        val preferencesProvider = PreferencesProvider(this)
        val gson: Gson = Gson()
        model = FeedbackModel(preferencesProvider, gson)
    }
}