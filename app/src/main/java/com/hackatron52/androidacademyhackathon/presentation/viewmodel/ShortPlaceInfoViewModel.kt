package com.hackatron52.androidacademyhackathon.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.FirebaseDatabase
import com.hackatron52.androidacademyhackathon.domain.Lce
import com.hackatron52.androidacademyhackathon.domain.PlaceDetailsRepository
import com.hackatron52.androidacademyhackathon.domain.models.PlaceDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val KEY_FAVORITES_NODE = "Favorites"

@HiltViewModel
class ShortPlaceInfoViewModel @Inject constructor() : ViewModel() {

    private val placeDetailsRepository = PlaceDetailsRepository.newInstance()
    val db = FirebaseDatabase.getInstance()

    private val _placeDetails = MutableSharedFlow<Lce<PlaceDetails>>()
    val placeDetails: SharedFlow<Lce<PlaceDetails>> get() = _placeDetails

    private val _isPlaceLiked = MutableLiveData<Boolean>()
    val isPlaceLiked: LiveData<Boolean> get() = _isPlaceLiked

    fun updateDetails(placeId: String) {
        viewModelScope.launch {
            _placeDetails.emitAll(placeDetailsRepository.observeNearbyPlaces(placeId))
        }
    }

    fun likePlace(placeDetails: PlaceDetails) {
        var isLiked = isPlaceLiked.value ?: false
        isLiked = !isLiked
        _isPlaceLiked.value = isLiked
        if (isLiked) {
            db.getReference(KEY_FAVORITES_NODE)
                .updateChildren(mapOf(placeDetails.placeId to placeDetails))
        } else {
        }
    }
}