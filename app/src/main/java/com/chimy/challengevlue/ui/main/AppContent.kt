package com.chimy.challengevlue.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import com.chimy.challengevlue.ui.favorites.FavoriteScreen
import com.chimy.challengevlue.ui.main.viewmodel.MapViewModel

/**
 * Entry point composable for the app that handles switching
 * between the map and the favorites list.
 */
@Composable
fun AppContent() {
    val context = LocalContext.current
    val viewModel = remember { MapViewModel() }

    var showFavorites by remember { mutableStateOf(false) }

    if (showFavorites) {
        FavoriteScreen(
            viewModel = viewModel,
            onBack = { showFavorites = false }
        )
    } else {
        Box(Modifier.fillMaxSize()) {
            MapScreen(context = context, viewModel = viewModel)

            FloatingActionButton(
                onClick = { showFavorites = true },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                Icon(Icons.Default.List, contentDescription = "Favorites")
            }
        }
    }
}