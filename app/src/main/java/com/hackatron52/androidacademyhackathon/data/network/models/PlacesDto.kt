package com.hackatron52.androidacademyhackathon.data.network.models

import com.google.gson.annotations.SerializedName

data class PlacesDto(
    @SerializedName("results")
    val places: List<PlaceDto>
)