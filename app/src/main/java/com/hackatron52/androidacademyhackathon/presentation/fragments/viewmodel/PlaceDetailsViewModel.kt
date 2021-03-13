package com.hackatron52.androidacademyhackathon.presentation.fragments.viewmodel

import com.hackatron52.androidacademyhackathon.presentation.fragments.model.PlaceDetailsCommand
import com.hackatron52.androidacademyhackathon.presentation.fragments.model.PlaceDetailsScreenState
import com.hackatron52.androidacademyhackathon.presentation.viewmodel.BaseViewModel

class PlaceDetailsViewModel :
    BaseViewModel<PlaceDetailsScreenState, PlaceDetailsCommand>(initialState = PlaceDetailsScreenState()) {
}