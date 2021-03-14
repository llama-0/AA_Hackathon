package com.hackatron52.androidacademyhackathon.data.network.models.routing

import com.google.gson.annotations.SerializedName
import com.hackatron52.androidacademyhackathon.domain.models.Location

data class StepDto(
    @SerializedName("start_location")
    val startLocation: Location,
    @SerializedName("end_location")
    val endLocation: Location
)