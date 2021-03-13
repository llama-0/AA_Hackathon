package com.hackatron52.androidacademyhackathon.di

import com.google.gson.Gson
import com.hackatron52.androidacademyhackathon.BuildConfig
import com.hackatron52.androidacademyhackathon.data.network.PlaceDetailsApi
import com.hackatron52.androidacademyhackathon.data.network.PlacesApi
import com.hackatron52.androidacademyhackathon.data.network.interceptors.MapsApiKeyInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://maps.googleapis.com/maps/api/place/"
private const val MAPS_API_KEY = "AIzaSyAwAEbJey1aDAd-7J6xWh08gVaypu12McM"

object NetworkDependencyProvider {

    private val retrofit = Retrofit.Builder()
        .client(provideOkHttpClient())
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    private val PLACES_API: PlacesApi = retrofit.create(PlacesApi::class.java)

    private val PLACE_DETAILS_API: PlaceDetailsApi = retrofit.create(PlaceDetailsApi::class.java)

    fun provideMapsApi(): PlacesApi {
        return PLACES_API
    }

    fun providePlaceDetailsApi(): PlaceDetailsApi {
        return PLACE_DETAILS_API
    }

    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                if (BuildConfig.DEBUG) {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                } else {
                    setLevel(HttpLoggingInterceptor.Level.NONE)
                }
            })
            .addInterceptor(MapsApiKeyInterceptor(MAPS_API_KEY))
            .build()
    }
}