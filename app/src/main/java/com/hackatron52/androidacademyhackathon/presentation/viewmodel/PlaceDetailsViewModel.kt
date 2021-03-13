package com.hackatron52.androidacademyhackathon.presentation.viewmodel

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.hackatron52.androidacademyhackathon.presentation.command.PlaceDetailsCommand
import com.hackatron52.androidacademyhackathon.presentation.model.PlaceDetailsScreenState

class PlaceDetailsViewModel :
    BaseViewModel<PlaceDetailsScreenState, PlaceDetailsCommand>(initialState = PlaceDetailsScreenState()) {

    private val db = FirebaseDatabase.getInstance()

    fun init(placeId: String?) {
        updateScreenState(placeId = placeId)
        //todo запрос на поиск place по id
    }

    private fun updateScreenState(
        placeId: String? = model.placeId,
        shouldRefreshView: Boolean = true
    ) {
        model = PlaceDetailsScreenState(placeId = placeId)
        if (shouldRefreshView) {
            refreshView()
        }
    }

    fun onFavoriteClicked() {
        Log.d("TAG", "onFavoriteClicked: ololo")
//        with (model) {
//            db.getReference("Favorites")
//                .updateChildren(mapOf(placeId to place))
//        }
    }

    fun onRouteClicked() {
        executeCommand(PlaceDetailsCommand.OpenMapWithRoute)
    }


}