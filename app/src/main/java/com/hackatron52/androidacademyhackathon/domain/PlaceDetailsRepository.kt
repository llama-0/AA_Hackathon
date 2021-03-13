package com.hackatron52.androidacademyhackathon.domain

import com.hackatron52.androidacademyhackathon.data.network.PlaceDetailsApi
import com.hackatron52.androidacademyhackathon.data.repo.BaseRepository
import com.hackatron52.androidacademyhackathon.di.NetworkDependencyProvider
import com.hackatron52.androidacademyhackathon.domain.mappers.PlaceDetailsMapper
import com.hackatron52.androidacademyhackathon.domain.models.PlaceDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class PlaceDetailsRepository(
    private val placeDetailsApi: PlaceDetailsApi,
    private val placeDetailsMapper: PlaceDetailsMapper,
) : BaseRepository() {

    suspend fun observeNearbyPlaces(
        placeId: String,
    ): Flow<Lce<PlaceDetails>> =
        withContext(Dispatchers.IO) {
            safeApiCall {
                val placeDetails = placeDetailsApi.getPlaceDetails(placeId).placeDetails
                placeDetailsMapper.toDomainModel(placeDetails)
            }
        }

    companion object {
        fun newInstance(): PlaceDetailsRepository {
            return PlaceDetailsRepository(
                NetworkDependencyProvider.providePlaceDetailsApi(),
                PlaceDetailsMapper.newInstance()
            )
        }
    }
}