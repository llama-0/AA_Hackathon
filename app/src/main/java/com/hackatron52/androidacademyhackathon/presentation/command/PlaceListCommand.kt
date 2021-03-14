package com.hackatron52.androidacademyhackathon.presentation.command

import android.view.View
import com.hackatron52.androidacademyhackathon.presentation.navigation.Command

sealed class PlaceListCommand : Command {
    class ShowPlaceDetail(val id: String, val view: View) : PlaceListCommand()
}