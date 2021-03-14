package com.hackatron52.androidacademyhackathon.data.network

import com.hackatron52.androidacademyhackathon.data.network.models.routing.DirectionResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface DirectionsApi {

    @GET("directions/json?")
    suspend fun getPlaceDetails(
        @Query("origin") startPlace: String,
        @Query("destination") endPlace: String,
        @Query("mode") directionsMode: String = "walking"
    ): DirectionResponseDto
}