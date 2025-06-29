package com.chimy.challengevlue.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AreaChart
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Satellite
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.GoogleMap

@Composable
fun MapTypeSelector(
    currentType: Int,
    onTypeSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }


    Row(
        modifier = modifier.wrapContentSize(Alignment.CenterStart),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            FloatingActionButton(
                onClick = { expanded = !expanded },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Layers,
                    contentDescription = "Change Map Type"
                )
            }
            Text(
                text = "Type",
                style = MaterialTheme.typography.labelSmall
            )
        }
        AnimatedVisibility(visible = expanded) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MapTypeIconButton(
                    icon = Icons.Default.Map,
                    label = "Normal",
                    isSelected = currentType == GoogleMap.MAP_TYPE_NORMAL,
                    onClick = {
                        onTypeSelected(GoogleMap.MAP_TYPE_NORMAL)
                        expanded = false
                    }
                )
                MapTypeIconButton(
                    icon = Icons.Default.Satellite,
                    label = "Satellite",
                    isSelected = currentType == GoogleMap.MAP_TYPE_SATELLITE,
                    onClick = {
                        onTypeSelected(GoogleMap.MAP_TYPE_SATELLITE)
                        expanded = false
                    }
                )
                MapTypeIconButton(
                    icon = Icons.Default.AreaChart,
                    label = "Hybrid",
                    isSelected = currentType == GoogleMap.MAP_TYPE_HYBRID,
                    onClick = {
                        onTypeSelected(GoogleMap.MAP_TYPE_HYBRID)
                        expanded = false
                    }
                )
            }
        }
    }
}


@Composable
fun MapTypeIconButton(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(48.dp)
                .background(
                    if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)
                    else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
        Text(
            label,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
