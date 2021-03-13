package com.hackatron52.androidacademyhackathon.data.network

import com.hackatron52.androidacademyhackathon.data.network.models.PlacesDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlacesApi {

    @GET("nearbysearch/json?")
    fun getNearbyPlaces(
        @Query("location") location: String,
        @Query("types") types: String,
        @Query("radius") radius: Int,
    ): Call<PlacesDto>
}