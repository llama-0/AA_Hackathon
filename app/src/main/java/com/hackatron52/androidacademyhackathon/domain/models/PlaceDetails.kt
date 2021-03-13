package com.hackatron52.androidacademyhackathon.domain.models

data class PlaceDetails(
    val adr_address: String,
    val formatted_address: String,
    val icon: String,
    val name: String,
    val photos: List<Photo>,
    val rating: Double
)