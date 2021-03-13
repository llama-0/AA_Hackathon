package com.hackatron52.androidacademyhackathon.presentation.viewmodel

import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.hackatron52.androidacademyhackathon.domain.Lce
import com.hackatron52.androidacademyhackathon.domain.PlacesRepository
import com.hackatron52.androidacademyhackathon.domain.models.Place
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.emitAll
import javax.inject.Inject

@HiltViewModel
class MapFragmentViewModel @Inject constructor() :
    ViewModel() {
    private val placesRepository: PlacesRepository = PlacesRepository.newInstance()

    private val _currentUserLocation = MutableLiveData<Location>()
    val currentUserLocation: LiveData<Location> get() = _currentUserLocation

    private val _nearbyPlaces = MutableSharedFlow<Lce<List<Place>>>()
    val nearbyPlaces: SharedFlow<Lce<List<Place>>> get() = _nearbyPlaces

    suspend fun updateNearbyPlaces(
        location: Location,
        type: String,
        radius: Int
    ) {
        _nearbyPlaces.emitAll(placesRepository.observeNearbyPlaces(location, type, radius))
    }

    fun updateUserLocation(fusedLocationProviderClient: FusedLocationProviderClient) {

        val mLocationRequest = LocationRequest.create()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        try {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                _currentUserLocation.value = location
            }
            fusedLocationProviderClient.requestLocationUpdates(
                mLocationRequest,
                object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        val mLastLocation = locationResult.lastLocation
                        _currentUserLocation.value = mLastLocation
                    }
                },
                Looper.myLooper() ?: return
            )

        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }
}