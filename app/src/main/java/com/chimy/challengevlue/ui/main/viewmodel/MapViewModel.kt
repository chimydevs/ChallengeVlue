package com.chimy.challengevlue.ui.main.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.chimy.challengevlue.domain.FavoriteLocation
import com.chimy.challengevlue.domain.usecase.FavoriteUseCase
import com.google.android.gms.maps.model.LatLng



/**
 * ths manages favorite locations.
 * keeps an in-memory list of favorites and exposes simple operations.
 */
class MapViewModel(
    private val favoriteUseCase: FavoriteUseCase
) : ViewModel() {

    private val _favorites = mutableStateListOf<FavoriteLocation>()
    val favorites: List<FavoriteLocation> = _favorites

    var userLocation: LatLng? = null
        private set

    init {
        _favorites.addAll(favoriteUseCase.getFavorites())
    }

    fun addFavorite(location: FavoriteLocation) {
        favoriteUseCase.addFavorite(location)
        _favorites.add(location)
    }

    fun removeFavorite(location: FavoriteLocation) {
        favoriteUseCase.removeFavorite(location)
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