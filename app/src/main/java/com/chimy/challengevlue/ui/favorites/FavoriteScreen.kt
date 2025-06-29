package com.chimy.challengevlue.ui.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chimy.challengevlue.ui.main.viewmodel.FavoriteLocation
import com.chimy.challengevlue.ui.main.viewmodel.MapViewModel
import com.chimy.challengevlue.ui.theme.ChallengeVlueTheme
import com.google.android.gms.maps.model.LatLng

/**
 * Screen that displays the list of favorite locations.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    viewModel: MapViewModel,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favorites") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize()
        ) {
            items(viewModel.favorites) { favorite ->
                FavoriteItem(favorite) {
                    viewModel.removeFavorite(favorite)
                }
            }
        }
    }
}

@Composable
fun FavoriteItem(
    favorite: FavoriteLocation,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(favorite.title, style = MaterialTheme.typography.titleMedium)
                Text(
                    "Lat: ${favorite.latLng.latitude.format(4)}, Lng: ${favorite.latLng.longitude.format(4)}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}

private fun Double.format(digits: Int) = "%.${digits}f".format(this)

@Preview(showBackground = true)
@Composable
fun FavoriteScreenPreview() {
    // Fake data to simulate favorites
    val fakeFavorites = listOf(
        FavoriteLocation("Home", LatLng(25.76, -80.19)),
    )

    // Fake ViewModel just for preview
    val fakeViewModel = object : MapViewModel() {
        override val favorites: List<FavoriteLocation>
            get() = fakeFavorites
    }

    ChallengeVlueTheme {
        FavoriteScreen(
            viewModel = fakeViewModel,
            onBack = {}
        )
    }
}