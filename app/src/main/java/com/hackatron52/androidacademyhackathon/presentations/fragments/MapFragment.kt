package com.hackatron52.androidacademyhackathon.presentations.fragments

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
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.hackatron52.androidacademyhackathon.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : Fragment(R.layout.fragment_map) {

    private var locationPermissionGranted = false
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestLocationPermission()

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        showUserLocation()
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

    private fun showUserLocation() {
        requestNewLocationData { location ->
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
            }
        }
    }

    // TODO: probably move this out to new thread
    private fun requestNewLocationData(callback: (Location) -> Unit) {

        val mLocationRequest = LocationRequest.create()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        try {
            fusedLocationProviderClient.requestLocationUpdates(
                mLocationRequest,
                object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        val mLastLocation = locationResult.lastLocation
                        callback(mLastLocation)
                    }
                },
                Looper.myLooper() ?: return
            )

        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    companion object {
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
        private const val DEFAULT_ZOOM = 10f
    }
}