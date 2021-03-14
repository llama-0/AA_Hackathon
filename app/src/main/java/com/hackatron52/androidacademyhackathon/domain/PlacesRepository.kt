package com.hackatron52.androidacademyhackathon.domain

import android.location.Location
import com.hackatron52.androidacademyhackathon.data.network.PlacesApi
import com.hackatron52.androidacademyhackathon.data.repo.BaseRepository
import com.hackatron52.androidacademyhackathon.di.NetworkDependencyProvider
import com.hackatron52.androidacademyhackathon.domain.mappers.PlaceMapper
import com.hackatron52.androidacademyhackathon.domain.models.Place
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class PlacesRepository(
    private val placesApi: PlacesApi,
    private val placeMapper: PlaceMapper,
) : BaseRepository() {

    suspend fun observeNearbyPlaces(
        location: Location,
        type: String,
        radius: Int
    ): Flow<Lce<List<Place>>> =
        withContext(Dispatchers.IO) {
            safeApiCall {
                val places = placesApi.getNearbyPlaces(
                    "${location.latitude},${location.longitude}",
                    type,
                    radius
                ).places

                places.map { placeMapper.toDomainModel(it) }
//                    .filter { it.openingHours?.openNow ?: false }
                    .sortedByDescending { it.rating }
                    .subList(0, 10.coerceAtMost(places.size))
            }
        }

    companion object {
        fun newInstance(): PlacesRepository {
            return PlacesRepository(
                NetworkDependencyProvider.provideMapsApi(),
                PlaceMapper.newInstance()
            )
        }
    }
}