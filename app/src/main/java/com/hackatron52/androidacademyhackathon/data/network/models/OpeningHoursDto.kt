package com.hackatron52.androidacademyhackathon.data.network.models

import com.google.gson.annotations.SerializedName

data class OpeningHoursDto(
    @SerializedName("open_now")
    val openNow: Boolean
)