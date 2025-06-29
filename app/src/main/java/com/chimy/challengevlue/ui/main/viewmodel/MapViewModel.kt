package com.chimy.challengevlue.ui.main.viewmodel

import androidx.compose.runtime.mutableStateListOf
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

    private val _favorites = mutableStateListOf<FavoriteLocation>()
    open val favorites: List<FavoriteLocation> get() = _favorites

    var userLocation: LatLng? = null
        private set

    fun addFavorite(location: FavoriteLocation) {
        _favorites.add(location)
    }

    fun removeFavorite(location: FavoriteLocation) {
        _favorites.remove(location)
    }

    fun updateFavoriteTitle(favorite: FavoriteLocation, newTitle: String) {
        val index = _favorites.indexOf(favorite)
        if (index != -1) {
            _favorites[index] = favorite.copy(title = newTitle)
        }
    }

    fun setUserLocation(latLng: LatLng) {
        userLocation = latLng
    }

}