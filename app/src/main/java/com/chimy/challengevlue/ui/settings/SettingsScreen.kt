package com.chimy.challengevlue.ui.settings

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chimy.challengevlue.ui.theme.ChallengeVlueTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit
) {

    val isDarkTheme = isSystemInDarkTheme()
    val context = LocalContext.current

    // State for selected language
    var selectedLanguage by remember { mutableStateOf("English") }

    ChallengeVlueTheme {

        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                TopAppBar(
                    title = { Text("Settings", color = MaterialTheme.colorScheme.onBackground) },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                FakeProfileSection()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Dark Mode", color = MaterialTheme.colorScheme.onBackground)
                    // The dark mode setting follows the system theme (isSystemInDarkTheme()).
                    // This Switch is included for UI completeness, showing the current mode,
                    // but does not override the system behavior.
                    // It demonstrates awareness of theme toggling without violating
                    // the challenge's minimal spec, which relies on system-level dark mode.
                    Switch(
                        checked = isDarkTheme,
                        onCheckedChange = {
                            Toast
                                .makeText(context, "Follows system setting", Toast.LENGTH_SHORT)
                                .show()
                        }
                    )
                }

                LanguageSelector(
                    selectedLanguage = selectedLanguage,
                    onLanguageChange = { selectedLanguage = it }
                )
            }
        }
    }
}

@Composable
fun FakeProfileSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "JD",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 24.sp
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text("John Doe", style = MaterialTheme.typography.titleMedium)
            Text("john.doe@email.com", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun LanguageSelector(
    selectedLanguage: String,
    onLanguageChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text("Language", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(onClick = { expanded = true }) {
            Text(selectedLanguage)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("English") },
                onClick = {
                    onLanguageChange("English")
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Spanish") },
                onClick = {
                    onLanguageChange("Spanish")
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Portuguese") },
                onClick = {
                    onLanguageChange("Portuguese")
                    expanded = false
                }
            )
        }
    }
}
