package com.hackatron52.androidacademyhackathon.presentation.viewmodel

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.hackatron52.androidacademyhackathon.data.network.models.routing.RoutesDto
import com.hackatron52.androidacademyhackathon.domain.DirectionsRepository
import com.hackatron52.androidacademyhackathon.domain.Lce
import com.hackatron52.androidacademyhackathon.domain.PlacesRepository
import com.hackatron52.androidacademyhackathon.domain.models.Place
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapFragmentViewModel @Inject constructor() :
    ViewModel() {

    private val placesRepository: PlacesRepository = PlacesRepository.newInstance()
    private val directionsRepository: DirectionsRepository = DirectionsRepository.newInstance()

    private val _nearbyPlaces = MutableSharedFlow<Lce<List<Place>>>()
    val nearbyPlaces: SharedFlow<Lce<List<Place>>> get() = _nearbyPlaces

    private val _currentRoute = MutableSharedFlow<Lce<RoutesDto>>()
    val currentRoute: SharedFlow<Lce<RoutesDto>> get() = _currentRoute

    suspend fun updateNearbyPlaces(
        location: Location,
        type: String,
        radius: Int
    ) {
        _nearbyPlaces.emitAll(placesRepository.observeNearbyPlaces(location, type, radius))
    }

    fun loadRoute(startLocation: LatLng, endLocation: String) {
        viewModelScope.launch {
            _currentRoute.emitAll(
                directionsRepository.observeNearbyPlaces(
                    startLocation,
                    endLocation
                )
            )
        }
    }
}