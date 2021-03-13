package com.hackatron52.androidacademyhackathon.domain.mappers

import com.hackatron52.androidacademyhackathon.data.network.models.*
import com.hackatron52.androidacademyhackathon.domain.models.Location
import com.hackatron52.androidacademyhackathon.domain.models.OpeningHours
import com.hackatron52.androidacademyhackathon.domain.models.Photo
import com.hackatron52.androidacademyhackathon.domain.models.Place

class PlaceMapper private constructor(
    private val geometryMapper: GeometryMapper,
    private val openingHoursMapper: OpeningHoursMapper,
    private val photoMapper: PhotoMapper
) {

    fun toDomainModel(placeDto: PlaceDto): Place {
        with(placeDto) {
            return Place(
                geometryMapper.toDomainModel(geometry),
                icon,
                name,
                photoMapper.toDomainModel(photos),
                placeID,
                rating,
                reference,
                scope,
                types,
                userRatingsTotal,
                openingHours?.let { openingHoursMapper.toDomainModel(it) }
            )
        }
    }

    companion object {
        fun newInstance(): PlaceMapper {
            return PlaceMapper(
                GeometryMapper(LocationMapper()),
                OpeningHoursMapper(),
                PhotoMapper()
            )
        }
    }
}

class GeometryMapper(private val locationMapper: LocationMapper) {
    fun toDomainModel(geometryDto: GeometryDto): Location {
        return locationMapper.toDomainModel(geometryDto.location)
    }
}

class LocationMapper {

    fun toDomainModel(locationDto: LocationDto): Location {
        return Location(locationDto.lat, locationDto.lng)
    }
}

class OpeningHoursMapper {

    fun toDomainModel(openingHoursDto: OpeningHoursDto): OpeningHours {
        return OpeningHours(openingHoursDto.openNow)
    }
}

class PhotoMapper {

    fun toDomainModel(photos: List<PhotoDto>?): List<Photo> {
        return photos?.map {
            Photo(it.height, url(it.photoReference), it.width)
        } ?: emptyList()
    }

    private fun url(photoReference: String): String {
        return "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=$photoReference&key=AIzaSyAwAEbJey1aDAd-7J6xWh08gVaypu12McM"
    }
}