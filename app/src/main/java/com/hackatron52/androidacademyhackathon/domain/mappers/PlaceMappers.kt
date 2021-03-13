package com.hackatron52.androidacademyhackathon.domain.mappers

import com.hackatron52.androidacademyhackathon.data.network.models.*
import com.hackatron52.androidacademyhackathon.domain.models.*

class PlaceMapper private constructor(
    private val geometryMapper: GeometryMapper,
    private val openingHoursMapper: OpeningHoursMapper,
    private val photoMapper: PhotoMapper
) {

    fun toDomainModel(placeDto: PlaceDto): Place {
        with(placeDto) {
            return Place(
                businessStatus,
                geometryMapper.toDomainModel(geometry),
                icon,
                name,
                photos.map { photoMapper.toDomainModel(it) },
                placeID,
                rating,
                reference,
                scope,
                types,
                userRatingsTotal,
                vicinity,
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
    fun toDomainModel(geometryDto: GeometryDto): Geometry {
        return Geometry(locationMapper.toDomainModel(geometryDto.location))
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

    fun toDomainModel(photoDto: PhotoDto): Photo {
        return Photo(photoDto.height, photoDto.photoReference, photoDto.width)
    }
}