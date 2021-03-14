package com.hackatron52.androidacademyhackathon.data.network

import com.hackatron52.androidacademyhackathon.data.network.models.PlaceDetailsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

private const val DEFAULT_PLACE_DETAILS_FIELDS =
    "place_id,name,rating,photo,adr_address,formatted_address,icon"

interface PlaceDetailsApi {

    @GET("place/details/json?")
    suspend fun getPlaceDetails(
        @Query("place_id") placeId: String,
        @Query("fields") fields: String = DEFAULT_PLACE_DETAILS_FIELDS
    ): PlaceDetailsResponseDto
}