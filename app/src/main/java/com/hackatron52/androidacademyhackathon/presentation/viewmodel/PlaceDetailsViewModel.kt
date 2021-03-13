package com.hackatron52.androidacademyhackathon.presentation.viewmodel

import com.hackatron52.androidacademyhackathon.presentation.command.PlaceDetailsCommand
import com.hackatron52.androidacademyhackathon.presentation.model.PlaceDetailsScreenState

class PlaceDetailsViewModel :
    BaseViewModel<PlaceDetailsScreenState, PlaceDetailsCommand>(initialState = PlaceDetailsScreenState()) {

    fun init(placeId: String?) {
        updateScreenState(placeId = placeId)
        //todo запрос на поиск place по id
    }

    fun updateScreenState(
        placeId: String? = model.placeId,
        shoouldRefreshView: Boolean = true
    ) {
        model = PlaceDetailsScreenState(placeId = placeId)
        if (shoouldRefreshView) {
            refreshView()
        }
    }

    fun onFavoriteClicked() {
        model.placeId?.let {
            executeCommand(PlaceDetailsCommand.AddPlaceToFavorites(it))
        }
    }

    fun onRouteClicked() {
        executeCommand(PlaceDetailsCommand.OpenMapWithRoute)
    }


}