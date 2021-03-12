package com.hackatron52.androidacademyhackathon.data.network

import com.hackatron52.androidacademyhackathon.data.model.ApiPlace
import com.hackatron52.androidacademyhackathon.data.model.Location
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("replace_with_api_path")
    fun getPlaces(): Single<List<ApiPlace>>

    @GET("...")
    fun getUserLocation(): Single<Location>

    @POST("...")
    fun addFavoritePlace(@Body body: ApiPlace): Completable
}