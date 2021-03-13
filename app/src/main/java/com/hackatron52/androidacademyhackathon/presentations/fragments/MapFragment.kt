package com.hackatron52.androidacademyhackathon.presentations.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
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
                requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    private fun showUserLocation() {
        loadUserLocation { location ->
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

    private fun loadUserLocation(callback: (Location) -> Unit) {
        if (locationPermissionGranted) {
            try {
                val task = fusedLocationProviderClient.lastLocation
                task.addOnSuccessListener { location ->
                    location?.let { callback(location) }
                }
            } catch (e: SecurityException) {
                Log.e("Exception: %s", e.message, e)
            }
        }
    }

    companion object {
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
        private const val DEFAULT_ZOOM = 10f
    }
}