package com.hackatron52.androidacademyhackathon.presentation.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.FirebaseDatabase
import com.hackatron52.androidacademyhackathon.R
import com.hackatron52.androidacademyhackathon.domain.models.Place
import com.hackatron52.androidacademyhackathon.presentation.dialog.ShortPlaceInfoDialog
import com.hackatron52.androidacademyhackathon.presentation.viewmodel.MapFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MapFragment : Fragment(R.layout.fragment_map) {

    private val mapFragmentViewModel: MapFragmentViewModel by viewModels()

    private var locationPermissionGranted = false
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var mapFragment: SupportMapFragment? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestLocationPermission()
        initObservers()

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        mapFragment = childFragmentManager
            .findFragmentById(R.id.google_map) as? SupportMapFragment

        if (locationPermissionGranted) {
            mapFragmentViewModel.updateUserLocation(fusedLocationProviderClient)
        }
    }

    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext().applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    private fun initObservers() {
        mapFragmentViewModel.currentUserLocation.observe(viewLifecycleOwner, { location ->
            location?.let(::showUserLocation)
        })
        lifecycleScope.launchWhenStarted {
            mapFragmentViewModel.nearbyPlaces.collect { lce ->
                if (lce.isFinishedSuccessfully) {
                    lce.content?.let(::showPlaces)
                } else {
                    lce.error?.printStackTrace()
                }
            }
        }
    }

    private fun showUserLocation(location: Location) {
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.google_map) as? SupportMapFragment
        mapFragment?.getMapAsync { googleMap ->
            val userLocation = LatLng(location.latitude, location.longitude)
            googleMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    userLocation,
                    DEFAULT_ZOOM
                )
            )
            try {
                googleMap.isMyLocationEnabled = true
            } catch (e: SecurityException) {
                Log.e("Exception: %s", e.message, e)
            }
            lifecycleScope.launchWhenCreated {
                mapFragmentViewModel.updateNearbyPlaces(
                    location,
                    DEFAULT_PLACE_TYPES,
                    DEFAULT_PLACE_RADIUS
                )
            }
        }
    }

    private fun showPlaces(places: List<Place>) {
        mapFragment?.getMapAsync { googleMap ->
            places.forEach { place ->
                val placeLocation = LatLng(place.location.lat, place.location.lng)
                googleMap.addMarker(
                    MarkerOptions().title(place.name).position(placeLocation).snippet(place.placeID)
                )
                val db = FirebaseDatabase.getInstance().getReference("Places")
                db.updateChildren(mapOf(place.placeID to place))
                val marker =
                    MarkerOptions().title(place.name).position(placeLocation).snippet(place.placeID)
                googleMap.addMarker(marker)
            }
            googleMap.setOnMarkerClickListener {
                showPlaceInfo(it.snippet)
                true
            }
        }
    }

    private fun showPlaceInfo(placeId: String) {
        ShortPlaceInfoDialog.newInstance(placeId).show(parentFragmentManager, placeId)
    }

    companion object {
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
        private const val DEFAULT_ZOOM = 10f
        private const val DEFAULT_PLACE_TYPES = "restaurant"
        private const val DEFAULT_PLACE_RADIUS = 10000
    }
}