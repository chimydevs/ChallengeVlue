package com.chimy.challengevlue.di

import com.chimy.challengevlue.data.repository.FavoriteRepository
import com.chimy.challengevlue.domain.usecase.FavoriteUseCase
import com.chimy.challengevlue.ui.main.viewmodel.MapViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { FavoriteRepository() }
    single { FavoriteUseCase(get()) }
    viewModel { MapViewModel(get()) }
}