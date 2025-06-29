package com.chimy.challengevlue.domain

import com.google.android.gms.maps.model.LatLng

/**
 * Represents a favorite location selected by the user,
 * with a title and LatLng coordinates.
 */
data class FavoriteLocation(
    val title: String,
    val latLng: LatLng,
    val address: String
)
