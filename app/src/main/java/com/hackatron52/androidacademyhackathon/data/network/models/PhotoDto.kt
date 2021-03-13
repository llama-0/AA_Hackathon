package com.hackatron52.androidacademyhackathon.data.network.models

import com.google.gson.annotations.SerializedName

data class PhotoDto(
    val height: Long,
    @SerializedName("photo_reference")
    val photoReference: String,
    val width: Long
)