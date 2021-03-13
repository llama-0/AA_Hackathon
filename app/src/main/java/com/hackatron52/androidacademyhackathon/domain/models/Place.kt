package com.hackatron52.androidacademyhackathon.domain.models

data class Place(
    val businessStatus: String,
    val geometry: Geometry,
    val icon: String,
    val name: String,
    val photos: List<Photo>,
    val placeID: String,
    val rating: Double,
    val reference: String,
    val scope: String,
    val types: List<String>,
    val userRatingsTotal: Long,
    val vicinity: String,
    val openingHours: OpeningHours? = null
)