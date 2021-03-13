package com.hackatron52.androidacademyhackathon.presentation.model

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hackatron52.androidacademyhackathon.data.network.PreferencesProvider
import java.lang.reflect.Type

class FeedbackModel(private val preferencesProvider: PreferencesProvider, private val gson: Gson) {
    private val feedbackList: MutableList<Feedback> = mutableListOf()

    fun addFeedback(feedback: Feedback) {
        feedbackList.add(feedback)
    }

    fun getListFeedbackFromModel(): List<Feedback> {
        return feedbackList
    }

    fun saveSharedPref(feedbackList: String) {
        preferencesProvider.putString(PreferencesProvider.KEY_STR_FEEDBACK, feedbackList)
    }

    fun getListFromSharedPref(): String? {
        return (preferencesProvider.getString(PreferencesProvider.KEY_STR_FEEDBACK))
    }

    fun checkKeyFeedbackList(): Boolean {
        return preferencesProvider.hasKey(PreferencesProvider.KEY_STR_FEEDBACK)
    }

    fun clearFeedbackList() {
        return preferencesProvider.clearListFeedback()
    }

    fun getFeedbackListFromSharedPref(): List<Feedback> {
        val type: Type = object : TypeToken<List<Feedback?>?>() {}.type
        return gson.fromJson(getListFromSharedPref(), type)
    }

}