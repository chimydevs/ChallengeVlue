package com.chimy.challengevlue.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

/**
 * Displays a GoogleMap centered on Miami.
 */

@SuppressLint("MissingPermission") // we handle permission manually
@Composable
fun MapScreen(
    context: Context,
    modifier: Modifier = Modifier
) {
    // Remember a MapView tied to the Compose lifecycle
    val mapView = rememberMapViewWithLifecycle(context)

    // State to hold the GoogleMap once initialized
    var googleMap by remember { mutableStateOf<GoogleMap?>(null) }

    // ActivityResult API to request location permission
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                enableUserLocation(context, googleMap)
            }
        }
    )

    // Check location permission once googleMap is ready
    LaunchedEffect(Unit) {
        while (googleMap == null) {
            kotlinx.coroutines.delay(100)
        }
        if (hasLocationPermission(context)) {
            enableUserLocation(context, googleMap)
        } else {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    // Display the MapView inside Compose
    AndroidView(
        factory = { mapView },
        modifier = modifier
    ) { mapView ->
        mapView.getMapAsync { gMap ->
            googleMap = gMap
            setupMap(gMap, context)
        }
    }
}


//Sets up the initial map state: centers on Miami and shows a marker.
private fun setupMap(googleMap: GoogleMap, context: Context) {
    val defaultLatLng = LatLng(25.7617, -80.1918) // Miami coordinates
    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLatLng, 12f))
    googleMap.addMarker(
        MarkerOptions()
            .position(defaultLatLng)
            .title("Miami")
    )

    if (hasLocationPermission(context)) {
        enableUserLocation(context, googleMap)
    }
}


//Enables showing the user's location on the map and moves the camera to it.
@SuppressLint("MissingPermission")
private fun enableUserLocation(context: Context, googleMap: GoogleMap?) {
    googleMap?.isMyLocationEnabled = true

    val fusedClient = LocationServices.getFusedLocationProviderClient(context)
    fusedClient.lastLocation.addOnSuccessListener { location ->
        location?.let {
            val userLatLng = LatLng(it.latitude, it.longitude)
            googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 14f))
            googleMap?.addMarker(
                MarkerOptions()
                    .position(userLatLng)
                    .title("You are here")
            )
        }
    }
}


//Checks if the app currently has location permission granted.
private fun hasLocationPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}