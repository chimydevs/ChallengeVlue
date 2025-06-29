package com.chimy.challengevlue.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween


@Composable
fun BottomNavLayout(
    currentScreen: String,
    onNavigate: (String) -> Unit,
    content: @Composable () -> Unit,
    visible: Boolean = true,
) {
    Box(Modifier.fillMaxSize()) {
        content()

        AnimatedVisibility(
            visible = visible,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            BottomNavBar(
                currentScreen = currentScreen,
                onNavigate = onNavigate,
            )
        }
    }
}

@Composable
fun BottomNavBar(
    currentScreen: String,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    Box(
        modifier
            .clip(RoundedCornerShape(50))
            .background(colors.surface.copy(alpha = 0.85f))
            .padding(horizontal = 32.dp, vertical = 12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = { onNavigate("map") }) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = if (currentScreen == "map") colors.tertiary else colors.primary
                )
            }
            IconButton(onClick = { onNavigate("favorites") }) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorites",
                    tint = if (currentScreen == "favorites") colors.tertiary else colors.primary
                )
            }
            IconButton(onClick = { onNavigate("profile") }) {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = "Profile",
                    tint = if (currentScreen == "profile") colors.tertiary else colors.primary
                )
            }
        }
    }
}

@Preview
@Composable
fun BottomNavBarPreview() {
    BottomNavBar(currentScreen = "map", onNavigate = {})
}

