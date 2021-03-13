package com.hackatron52.androidacademyhackathon.data.network.models

import com.google.gson.annotations.SerializedName

data class PlaceDto(
    @SerializedName("business_status")
    val businessStatus: String,
    val geometry: GeometryDto,
    val icon: String,
    val name: String,
    val photos: List<PhotoDto>,

    @SerializedName("place_id")
    val placeID: String,

    val rating: Double,
    val reference: String,
    val scope: String,
    val types: List<String>,

    @SerializedName("user_ratings_total")
    val userRatingsTotal: Long,

    val vicinity: String,

    @SerializedName("opening_hours")
    val openingHours: OpeningHoursDto? = null
)