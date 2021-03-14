package com.hackatron52.androidacademyhackathon.presentation.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.hackatron52.androidacademyhackathon.R
import com.hackatron52.androidacademyhackathon.domain.models.Place
import com.hackatron52.androidacademyhackathon.domain.models.PlaceDetails
import com.hackatron52.androidacademyhackathon.presentation.dialog.ShortPlaceInfoDialog
import com.hackatron52.androidacademyhackathon.presentation.viewmodel.MapFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MapFragment : Fragment(R.layout.fragment_map), ShortPlaceInfoDialog.PlaceRouteListener {

    private val mapFragmentViewModel: MapFragmentViewModel by viewModels()

    private var locationPermissionGranted = false
    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest

    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            showUserLocation(locationResult.lastLocation)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestLocationPermission()
        initLocationRequest()
        initObservers()

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.google_map) as? SupportMapFragment
        mapFragment?.getMapAsync {
            googleMap = it
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
        mapFragmentViewModel.routeStatus.observe(viewLifecycleOwner, {

        })
        lifecycleScope.launchWhenStarted {
            mapFragmentViewModel.currentRoute.collect { lce ->
                if (lce.isFinishedSuccessfully) {
                    lce.content?.legs?.let { legs ->
                        val polyline = PolylineOptions()
                        polyline.color(ContextCompat.getColor(requireContext(), R.color.orange))
                        legs.firstOrNull()?.steps?.forEach {
                            polyline.add(
                                LatLng(it.startLocation.lat, it.startLocation.lng),
                                LatLng(it.endLocation.lat, it.endLocation.lng)
                            )
                        }
                        googleMap.clear()
                        googleMap.addMarker(MarkerOptions().position(polyline.points.last()))
                        googleMap.addPolyline(polyline)
                    }
                } else {
                    lce.error?.printStackTrace()
                }
            }
        }
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

    override fun route(placeDetails: PlaceDetails) {
        try {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                val latLng = LatLng(location.latitude, location.longitude)
                mapFragmentViewModel.loadRoute(latLng, placeDetails.placeId)
                mapFragmentViewModel.startRoute(placeDetails)
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    override fun onStart() {
        super.onStart()
        startLocationUpdates()
    }

    override fun onStop() {
        super.onStop()
        stopLocationUpdates()
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            showUserLocation(location, true)
        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    private fun initLocationRequest() {
        locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 500
        locationRequest.fastestInterval = 500
    }

    private fun showUserLocation(location: Location, animateZoom: Boolean = false) {
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.google_map) as? SupportMapFragment
        mapFragment?.getMapAsync { googleMap ->
            val userLocation = LatLng(location.latitude, location.longitude)
            if (animateZoom) {
                googleMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        userLocation,
                        DEFAULT_ZOOM
                    )
                )
            }
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
        places.forEach { place ->
            val placeLocation = LatLng(place.location.lat, place.location.lng)
            val marker =
                MarkerOptions().title(place.name).position(placeLocation).snippet(place.placeID)
            googleMap.addMarker(marker)
        }
        googleMap.setOnMarkerClickListener { marker ->
            showPlaceInfo(marker.snippet)
            true
        }
    }

    private fun showPlaceInfo(placeId: String) {
        ShortPlaceInfoDialog.newInstance(placeId, this).show(parentFragmentManager, placeId)
    }

    companion object {
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
        private const val DEFAULT_ZOOM = 17f
        private const val DEFAULT_PLACE_TYPES = "restaurant"
        private const val DEFAULT_PLACE_RADIUS = 10000
    }
}