package com.hackatron52.androidacademyhackathon.presentation

enum class PlaceListStatus {
    Favorites,
    History;

    companion object {
        fun getStatusByOrdinalNumber(ordinal: Int): PlaceListStatus =
            values()[ordinal]
    }
}