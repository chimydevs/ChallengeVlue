package com.chimy.challengevlue.ui.favorites

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.chimy.challengevlue.ui.components.EditFavoriteDialog
import com.chimy.challengevlue.ui.components.FavoriteItem
import com.chimy.challengevlue.ui.main.viewmodel.FavoriteLocation
import com.chimy.challengevlue.ui.main.viewmodel.MapViewModel

/**
 * Screen that displays the list of favorite locations.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    viewModel: MapViewModel,
    onBack: () -> Unit
) {
    var showEditDialog by remember { mutableStateOf(false) }
    var selectedFavorite by remember { mutableStateOf<FavoriteLocation?>(null) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("Favorites") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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
                FavoriteItem(
                    favorite = favorite,
                    onDelete = { viewModel.removeFavorite(favorite) },
                    onEdit = {
                        selectedFavorite = favorite
                        showEditDialog = true
                    }
                )
            }
        }
    }
    if (showEditDialog) {
        EditFavoriteDialog(
            currentTitle = selectedFavorite?.title.orEmpty(),
            onConfirm = { newTitle ->
                selectedFavorite?.let {
                    viewModel.updateFavoriteTitle(it, newTitle)
                }
                showEditDialog = false
            },
            onDismiss = { showEditDialog = false }
        )
    }
}