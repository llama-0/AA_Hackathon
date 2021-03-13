package com.hackatron52.androidacademyhackathon.presentation.fragments.viewmodel

import com.hackatron52.androidacademyhackathon.presentation.command.FeedbackCommand
import com.hackatron52.androidacademyhackathon.presentation.model.FeedbackScreenState
import com.hackatron52.androidacademyhackathon.presentation.viewmodel.BaseViewModel

class FeedbackViewModel :
    BaseViewModel<FeedbackScreenState, FeedbackCommand>(initialState = FeedbackScreenState("", emptyList()))
{
    fun init() {
        updateScreenState()
    }

    private fun updateScreenState(
        shouldRefreshView: Boolean = true
    ) {
//        model = FeedbackScreenState()
        if (shouldRefreshView) {
            refreshView()
        }
    }
}
