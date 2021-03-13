package com.hackatron52.androidacademyhackathon.data.network.models

import com.google.gson.annotations.SerializedName

data class PlaceDetailsDto(
    @SerializedName("adr_address") val adr_address: String,
    @SerializedName("formatted_address") val formatted_address: String,
    @SerializedName("icon") val icon: String,
    @SerializedName("name") val name: String,
    @SerializedName("photos") val photos: List<PhotoDto>?,
    @SerializedName("rating") val rating: Double
)