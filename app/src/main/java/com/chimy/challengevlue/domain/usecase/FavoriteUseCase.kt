package com.chimy.challengevlue.domain.usecase

import com.chimy.challengevlue.data.repository.FavoriteRepository
import com.chimy.challengevlue.domain.FavoriteLocation

class FavoriteUseCase(
    private val repository: FavoriteRepository
) {
    fun addFavorite(location: FavoriteLocation) {
        repository.addFavorite(location)
    }

    fun removeFavorite(location: FavoriteLocation) {
        repository.removeFavorite(location)
    }

    fun getFavorites(): List<FavoriteLocation> {
        return repository.getFavorites()
    }

    fun updateFavoriteTitle(favorite: FavoriteLocation, newTitle: String) {
        repository.updateFavoriteTitle(favorite, newTitle)
    }

}