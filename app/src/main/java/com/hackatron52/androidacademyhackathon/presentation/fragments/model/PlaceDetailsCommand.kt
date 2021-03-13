package com.hackatron52.androidacademyhackathon.presentation.fragments.model

import com.hackatron52.androidacademyhackathon.presentation.navigation.Command

sealed class PlaceDetailsCommand : Command {
    object OpenMapWithRoute : PlaceDetailsCommand()
    class AddPlaceToFavorites(id: String) : PlaceDetailsCommand()
}