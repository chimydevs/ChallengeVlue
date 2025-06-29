package com.chimy.challengevlue.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.chimy.challengevlue.ui.components.BottomNavLayout
import com.chimy.challengevlue.ui.favorites.FavoriteScreen
import com.chimy.challengevlue.ui.main.viewmodel.MapViewModel
import com.chimy.challengevlue.ui.settings.SettingsScreen
import org.koin.androidx.compose.koinViewModel

/**
 * Entry point composable for the app that handles switching
 * between the map and the favorites list.
 */
@Composable
fun AppContent( ) {
    val context = LocalContext.current
    val viewModel: MapViewModel = koinViewModel()


    var currentScreen by remember { mutableStateOf("map") }
    var isFullScreen by remember { mutableStateOf(false) }

    Box(Modifier.fillMaxSize()) {
        when (currentScreen) {
            "map" -> MapScreen(
                context = context,
                viewModel = viewModel,
                isFullScreen = isFullScreen,
                onToggleFullScreen = { isFullScreen = !isFullScreen }
            )

            "favorites" -> FavoriteScreen(
                onBack = { currentScreen = "map" }
            )

            "profile" -> SettingsScreen(
                onBack = { currentScreen = "map" }
            )
        }

        if (!isFullScreen) {
            BottomNavLayout(
                currentScreen = currentScreen,
                onNavigate = { currentScreen = it },
                content = {}
            )
        }
    }
}