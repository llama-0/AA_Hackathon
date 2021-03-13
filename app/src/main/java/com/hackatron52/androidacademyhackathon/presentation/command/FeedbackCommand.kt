package com.hackatron52.androidacademyhackathon.presentation.command

import com.hackatron52.androidacademyhackathon.presentation.navigation.Command

sealed class FeedbackCommand : Command {
    object SendFeedBack : FeedbackCommand()
}