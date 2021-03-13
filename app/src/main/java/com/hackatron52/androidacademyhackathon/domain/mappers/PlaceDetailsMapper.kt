package com.hackatron52.androidacademyhackathon.domain.mappers

import com.hackatron52.androidacademyhackathon.data.network.models.PlaceDetailsDto
import com.hackatron52.androidacademyhackathon.domain.models.PlaceDetails

class PlaceDetailsMapper(private val photoMapper: PhotoMapper) {

    fun toDomainModel(placeDetailsDto: PlaceDetailsDto): PlaceDetails {
        with(placeDetailsDto) {
            return PlaceDetails(
                adr_address,
                formatted_address,
                icon,
                name,
                photoMapper.toDomainModel(photos),
                rating
            )
        }
    }

    companion object {
        fun newInstance(): PlaceDetailsMapper {
            return PlaceDetailsMapper(PhotoMapper())
        }
    }
}