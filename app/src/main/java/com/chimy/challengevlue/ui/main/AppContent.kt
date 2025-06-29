package com.chimy.challengevlue.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.chimy.challengevlue.ui.components.BottomNavLayout
import com.chimy.challengevlue.ui.favorites.FavoriteScreen
import com.chimy.challengevlue.ui.main.viewmodel.MapViewModel
import com.chimy.challengevlue.ui.settings.SettingsScreen

/**
 * Entry point composable for the app that handles switching
 * between the map and the favorites list.
 */
@Composable
fun AppContent() {
    val context = LocalContext.current
    val viewModel = remember { MapViewModel() }


    var currentScreen by remember { mutableStateOf("map") }

    BottomNavLayout(
        currentScreen = currentScreen,
        onNavigate = { currentScreen = it }
    ) {
        when (currentScreen) {
            "map" -> MapScreen(context, viewModel = viewModel)
            "favorites" -> FavoriteScreen(
                viewModel = viewModel,
                onBack = { currentScreen = "map" }
            )
            "profile" -> SettingsScreen(onBack = { currentScreen = "map" })
        }
    }

}