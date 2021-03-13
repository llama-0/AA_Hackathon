package com.hackatron52.androidacademyhackathon.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hackatron52.androidacademyhackathon.domain.Lce
import com.hackatron52.androidacademyhackathon.domain.PlaceDetailsRepository
import com.hackatron52.androidacademyhackathon.domain.models.PlaceDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShortPlaceInfoViewModel @Inject constructor() : ViewModel() {

    private val placeDetailsRepository = PlaceDetailsRepository.newInstance()

    private val _placeDetails = MutableSharedFlow<Lce<PlaceDetails>>()
    val placeDetails: SharedFlow<Lce<PlaceDetails>> get() = _placeDetails

    fun updateDetails(placeId: String) {
        viewModelScope.launch {
            _placeDetails.emitAll(placeDetailsRepository.observeNearbyPlaces(placeId))
        }
    }
}