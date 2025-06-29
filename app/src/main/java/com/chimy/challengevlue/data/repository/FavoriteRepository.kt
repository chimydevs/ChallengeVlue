package com.chimy.challengevlue.data.repository

import com.chimy.challengevlue.domain.FavoriteLocation

class FavoriteRepository {

    private val favorites = mutableListOf<FavoriteLocation>()

    fun addFavorite(location: FavoriteLocation) {
        favorites.add(location)
    }

    fun removeFavorite(location: FavoriteLocation) {
        favorites.remove(location)
    }

    fun getFavorites(): List<FavoriteLocation> {
        return favorites
    }

    fun updateFavoriteTitle(favorite: FavoriteLocation, newTitle: String) {
        val index = favorites.indexOf(favorite)
        if (index != -1) {
            favorites[index] = favorite.copy(title = newTitle)
        }
    }
}