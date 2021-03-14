package com.hackatron52.androidacademyhackathon.presentation.viewmodel

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.hackatron52.androidacademyhackathon.presentation.command.PlaceDetailsCommand
import com.hackatron52.androidacademyhackathon.presentation.model.Place
import com.hackatron52.androidacademyhackathon.presentation.model.PlaceDetailsScreenState

class PlaceDetailsViewModel :
    BaseViewModel<PlaceDetailsScreenState, PlaceDetailsCommand>(initialState = PlaceDetailsScreenState()) {

    private val db = FirebaseDatabase.getInstance()
    private val testData = Place("1", "Хлеб и Вино ", "http://old.leclick.ru/files/restaurants/photo/840050.jpg", " Ул Пушкина 1", 4.5, "", true)

    fun init(placeId: String?) {
        updateScreenState(placeId = placeId, place = testData)
        //todo запрос на поиск place по id
    }

    private fun updateScreenState(
        placeId: String? = model.placeId,
        place: Place? = model.place,
        shouldRefreshView: Boolean = true
    ) {
        model = PlaceDetailsScreenState(placeId = placeId, place = place)
        if (shouldRefreshView) {
            refreshView()
        }
    }

    fun onFavoriteClicked() {
        Log.d("TAG", "onFavoriteClicked: ${model.placeId}")
        with(model) {
            placeId?.let {
                db.getReference("Favorites")
                    .updateChildren(mapOf(placeId.toString() to place))
            }
        }
    }

    fun onRouteClicked() {
        executeCommand(PlaceDetailsCommand.OpenMapWithRoute)
    }

}