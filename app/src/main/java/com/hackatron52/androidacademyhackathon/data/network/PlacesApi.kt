package com.hackatron52.androidacademyhackathon.data.network

import com.hackatron52.androidacademyhackathon.data.network.models.PlacesDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PlacesApi {

    @GET("place/nearbysearch/json?")
    suspend fun getNearbyPlaces(
        @Query("location") location: String,
        @Query("type") type: String,
        @Query("radius") radius: Int,
    ): PlacesDto
}