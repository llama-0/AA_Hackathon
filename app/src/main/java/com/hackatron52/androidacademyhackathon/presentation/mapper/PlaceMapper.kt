package com.hackatron52.androidacademyhackathon.presentation.mapper

import com.hackatron52.androidacademyhackathon.domain.models.PlaceDetails
import com.hackatron52.androidacademyhackathon.presentation.model.Place

class PlaceMapper {

    fun mapList(placeDetails: List<PlaceDetails>): List<Place> =
        placeDetails.map(::map)

    fun map(placeDetails: PlaceDetails): Place =
        Place(
            placeDetails.placeId,
            placeDetails.name,
            placeDetails.photos.first().photoReference,
            placeDetails.adr_address,
            placeDetails.rating,
            "Описания пока что нет",
            true
        )
}