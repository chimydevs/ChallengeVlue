package com.chimy.challengevlue.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chimy.challengevlue.ui.main.viewmodel.FavoriteLocation
import com.chimy.challengevlue.ui.theme.ChallengeVlueTheme
import com.google.android.gms.maps.model.LatLng

@Composable
fun FavoriteItem(
    favorite: FavoriteLocation,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Icon(
                    imageVector = Icons.Default.Place,
                    contentDescription = "Location Icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Column {
                    Text(favorite.title, style = MaterialTheme.typography.titleMedium)
                    Text(
                        favorite.address,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        "Lat: ${favorite.latLng.latitude.format(4)}, Lng: ${favorite.latLng.longitude.format(4)}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    }
}

private fun Double.format(digits: Int) = "%.${digits}f".format(this)

@Preview
@Composable
fun FavoriteItemPreview() {
    ChallengeVlueTheme {
        FavoriteItem(
            favorite = FavoriteLocation("Home", LatLng(25.76, -80.19), "123 Main St"),
            onDelete = {},
            onEdit = {}
        )
    }
}