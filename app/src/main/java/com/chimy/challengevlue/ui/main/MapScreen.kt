package com.chimy.challengevlue.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.location.Geocoder
import android.location.Location
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.chimy.challengevlue.ui.components.MapTypeSelector
import com.chimy.challengevlue.ui.main.viewmodel.FavoriteLocation
import com.chimy.challengevlue.ui.main.viewmodel.MapViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.delay
import java.util.Locale

/**
 * Displays a GoogleMap centered on Miami.
 */

@SuppressLint("MissingPermission")
@Composable
fun MapScreen(
    context: Context,
    modifier: Modifier = Modifier,
    viewModel: MapViewModel = remember { MapViewModel() },
    isFullScreen: Boolean,
    onToggleFullScreen: () -> Unit
) {
    val mapView = rememberMapViewWithLifecycle(context)

    var googleMap by remember { mutableStateOf<GoogleMap?>(null) }
    var lastUserLatLng by remember { mutableStateOf<LatLng?>(null) }

    var showDialog by remember { mutableStateOf(false) }
    var pendingLatLng by remember { mutableStateOf<LatLng?>(null) }
    var pendingDistance by remember { mutableStateOf("") }

    var mapType by remember { mutableStateOf(GoogleMap.MAP_TYPE_NORMAL) }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) enableUserLocation(context, googleMap, viewModel)
        }
    )

    LaunchedEffect(Unit) {
        while (googleMap == null) delay(100)
        if (hasLocationPermission(context)) {
            enableUserLocation(context, googleMap, viewModel)
        } else {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    Box(Modifier.fillMaxSize()) {
        AndroidView(
            factory = { mapView },
            modifier = modifier
        ) { mv ->
            mv.getMapAsync { gMap ->
                googleMap = gMap

                gMap.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
                    override fun getInfoContents(marker: Marker): View? {
                        val context = mapView.context
                        return LinearLayout(context).apply {
                            orientation = LinearLayout.VERTICAL
                            setPadding(20, 10, 20, 10)

                            addView(TextView(context).apply {
                                text = marker.title
                                setTypeface(null, Typeface.BOLD)
                            })

                            addView(TextView(context).apply {
                                text = marker.snippet
                            })
                        }
                    }

                    override fun getInfoWindow(marker: Marker): View? = null
                })

                val defaultLatLng = LatLng(25.7617, -80.1918)
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLatLng, 12f))
                gMap.addMarker(
                    MarkerOptions()
                        .position(defaultLatLng)
                        .title("Miami")
                )

                if (hasLocationPermission(context)) {
                    val fusedClient = LocationServices.getFusedLocationProviderClient(context)
                    fusedClient.lastLocation.addOnSuccessListener { location ->
                        location?.let {
                            lastUserLatLng = LatLng(it.latitude, it.longitude)
                        }
                    }
                }

                for (favorite in viewModel.favorites) {
                    val snippet = viewModel.userLocation?.let { userLoc ->
                        val miles = userLoc.distanceToMiles(favorite.latLng)
                        "Distance: %.2f mi • %s".format(miles, favorite.address)
                    } ?: favorite.address


                    gMap.addMarker(
                        MarkerOptions()
                            .position(favorite.latLng)
                            .title(favorite.title)
                            .snippet(snippet)
                    )
                }

                gMap.setOnMapClickListener { latLng ->
                    val distance = viewModel.userLocation?.let { userLoc ->
                        val miles = userLoc.distanceToMiles(latLng)
                        "Distance: %.2f mi".format(miles)
                    } ?: "Unknown distance"


                    pendingLatLng = latLng
                    pendingDistance = distance
                    showDialog = true
                }
            }
        }

        LaunchedEffect(mapType) {
            googleMap?.mapType = mapType
        }

        FloatingActionButton(
            onClick = { onToggleFullScreen() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start =50.dp, top = 50.dp)
                .size(48.dp)
        ) {
            Icon(
                imageVector = if (isFullScreen) Icons.Default.FullscreenExit else Icons.Default.Fullscreen,
                contentDescription = if (isFullScreen) "Exit Fullscreen" else "Enter Fullscreen"
            )
        }

        Spacer(Modifier.height(16.dp))

        MapTypeSelector(
            currentType = mapType,
            onTypeSelected = { mapType = it },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start =50.dp, bottom =100.dp)
        )

        if (showDialog && pendingLatLng != null) {
            ShowAddFavoriteDialog(
                distanceText = pendingDistance,
                onConfirm = { inputName ->
                    val address = getAddressFromLatLng(context, pendingLatLng!!)

                    val marker = googleMap?.addMarker(
                        MarkerOptions()
                            .position(pendingLatLng!!)
                            .title(inputName)
                            .snippet("${pendingDistance}\n$address")
                    )
                    marker?.showInfoWindow()

                    viewModel.addFavorite(FavoriteLocation(inputName, pendingLatLng!!, address))
                    showDialog = false
                },
                onDismiss = { showDialog = false }
            )
        }
    }
}

@Composable
fun ShowAddFavoriteDialog(
    initialName: String = "",
    distanceText: String,
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var inputName by remember { mutableStateOf(initialName) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Save location") },
        text = {
            Column {
                OutlinedTextField(
                    value = inputName,
                    onValueChange = { inputName = it },
                    label = { Text("Enter place name") }
                )
                Spacer(Modifier.height(8.dp))
                Text(distanceText)
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm(inputName.ifEmpty { "Unnamed place" })
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

// Helpers
@SuppressLint("MissingPermission")
private fun enableUserLocation(
    context: Context,
    googleMap: GoogleMap?,
    viewModel: MapViewModel
) {
    googleMap?.isMyLocationEnabled = true
    val fusedClient = LocationServices.getFusedLocationProviderClient(context)
    fusedClient.lastLocation.addOnSuccessListener { location ->
        location?.let {
            val userLatLng = LatLng(it.latitude, it.longitude)
            viewModel.setUserLocation(userLatLng)
            googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 14f))
            googleMap?.addMarker(
                MarkerOptions()
                    .position(userLatLng)
                    .title("You are here")
            )
        }
    }
}

private fun hasLocationPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}

fun LatLng.distanceToMiles(other: LatLng): Float {
    val start = Location("").apply {
        latitude = this@distanceToMiles.latitude
        longitude = this@distanceToMiles.longitude
    }
    val end = Location("").apply {
        latitude = other.latitude
        longitude = other.longitude
    }
    val meters = start.distanceTo(end)
    return meters * 0.000621371f
}

fun getAddressFromLatLng(context: Context, latLng: LatLng): String {
    return try {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
        if (addresses != null && addresses.isNotEmpty()) {
            addresses[0].getAddressLine(0) ?: "Unknown address"
        } else {
            "Unknown address"
        }
    } catch (e: Exception) {
        "Unknown address"
    }
}