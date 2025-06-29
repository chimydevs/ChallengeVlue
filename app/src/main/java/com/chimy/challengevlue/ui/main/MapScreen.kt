package com.chimy.challengevlue.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
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
import com.chimy.challengevlue.ui.main.viewmodel.FavoriteLocation
import com.chimy.challengevlue.ui.main.viewmodel.MapViewModel
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
    modifier: Modifier = Modifier,
    viewModel: MapViewModel = remember { MapViewModel() }
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
            setupMap(gMap, context, viewModel)
        }
    }
}


//Sets up the initial map state: centers on Miami and shows a marker.
private fun setupMap(
    googleMap: GoogleMap,
    context: Context,
    viewModel: MapViewModel
) {
    val defaultLatLng = LatLng(25.7617, -80.1918)
    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLatLng, 12f))
    googleMap.addMarker(
        MarkerOptions()
            .position(defaultLatLng)
            .title("Miami")
    )

    if (hasLocationPermission(context)) {
        enableUserLocation(context, googleMap)
    }

    for (favorite in viewModel.favorites) {
        googleMap.addMarker(
            MarkerOptions()
                .position(favorite.latLng)
                .title(favorite.title)
        )
    }

    googleMap.setOnMapClickListener { latLng ->
        showAddFavoriteDialog(context, googleMap, viewModel, latLng)
    }
}
private fun Double.format(digits: Int) = "%.${digits}f".format(this)

private fun showAddFavoriteDialog(
    context: Context,
    googleMap: GoogleMap,
    viewModel: MapViewModel,
    latLng: LatLng
) {
    val activity = context as? ComponentActivity ?: return
    activity.runOnUiThread {
        var inputName = ""

        val editText = android.widget.EditText(context).apply {
            hint = "Enter place name"
        }

        android.app.AlertDialog.Builder(context)
            .setTitle("Save location")
            .setView(editText)
            .setPositiveButton("Save") { _, _ ->
                inputName = editText.text.toString().ifEmpty { "Unnamed place" }

                viewModel.addFavorite(FavoriteLocation(inputName, latLng))

                val marker = googleMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(inputName)
                        .snippet("Lat: ${latLng.latitude.format(4)}, Lng: ${latLng.longitude.format(4)}")
                )
                marker?.showInfoWindow()
            }
            .setNegativeButton("Cancel", null)
            .show()
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