package com.chimy.challengevlue.ui.main

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

/**
 * Displays a GoogleMap centered on Miami.
 */
@Composable
fun MapScreen(
    context: Context,
    modifier: Modifier = Modifier
) {
    val mapView = rememberMapViewWithLifecycle(context)

    AndroidView(
        factory = { mapView },
        modifier = modifier
    ) { mapView ->
        mapView.getMapAsync { googleMap ->
            setupMap(googleMap)
        }
    }
}

private fun setupMap(googleMap: GoogleMap) {
    val miamiLatLng = LatLng(25.7617, -80.1918) // Miami, FL
    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(miamiLatLng, 12f))

    // Example marker in Miami
    googleMap.addMarker(
        MarkerOptions()
            .position(miamiLatLng)
            .title("Miami")
    )
}