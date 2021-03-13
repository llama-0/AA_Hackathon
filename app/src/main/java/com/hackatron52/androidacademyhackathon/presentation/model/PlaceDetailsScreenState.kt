package com.hackatron52.androidacademyhackathon.presentation.model

class PlaceDetailsScreenState(
    val placeId: String? = null,
    val place: Place? = null
) : ScreenState {
    val isPlaceReadyToShow: Boolean = place != null

}