package com.hackatron52.androidacademyhackathon.domain

import com.google.android.gms.maps.model.LatLng
import com.hackatron52.androidacademyhackathon.data.network.DirectionsApi
import com.hackatron52.androidacademyhackathon.data.network.models.routing.RoutesDto
import com.hackatron52.androidacademyhackathon.data.repo.BaseRepository
import com.hackatron52.androidacademyhackathon.di.NetworkDependencyProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class DirectionsRepository(
    private val directionsApi: DirectionsApi
) : BaseRepository() {

    suspend fun observeNearbyPlaces(
        startLocation: LatLng,
        endPoint: String,
    ): Flow<Lce<RoutesDto>> =
        withContext(Dispatchers.IO) {
            safeApiCall {
                directionsApi.getPlaceDetails(
                    "${startLocation.latitude},${startLocation.longitude}",
                    "place_id:$endPoint"
                ).routes.first()
            }
        }

    companion object {
        fun newInstance(): DirectionsRepository {
            return DirectionsRepository(
                NetworkDependencyProvider.provideDirectionsApi()
            )
        }
    }
}