package com.hackatron52.androidacademyhackathon.presentation.viewmodel

import com.hackatron52.androidacademyhackathon.presentation.command.PlaceDetailsCommand
import com.hackatron52.androidacademyhackathon.presentation.model.PlaceDetailsScreenState

class PlaceDetailsViewModel :
    BaseViewModel<PlaceDetailsScreenState, PlaceDetailsCommand>(initialState = PlaceDetailsScreenState()) {
}