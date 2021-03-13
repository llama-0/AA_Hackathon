package com.hackatron52.androidacademyhackathon.data.network.models

import com.google.gson.annotations.SerializedName

data class PlaceDetailsResponseDto(
    @SerializedName("result") val placeDetails: PlaceDetailsDto,
)