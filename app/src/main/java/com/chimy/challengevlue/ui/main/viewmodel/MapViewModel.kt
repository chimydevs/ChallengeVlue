package com.chimy.challengevlue.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

/**
 * Represents a favorite location selected by the user,
 * with a title and LatLng coordinates.
 */
data class FavoriteLocation(
    val title: String,
    val latLng: LatLng
)

/**
 * ths manages favorite locations.
 * keeps an in-memory list of favorites and exposes simple operations.
 */
open class MapViewModel : ViewModel() {

    private val _favorites = mutableListOf<FavoriteLocation>()
    open val favorites: List<FavoriteLocation> get() = _favorites

    fun addFavorite(location: FavoriteLocation) {
        _favorites.add(location)
    }

    fun removeFavorite(location: FavoriteLocation) {
        _favorites.remove(location)
    }
}