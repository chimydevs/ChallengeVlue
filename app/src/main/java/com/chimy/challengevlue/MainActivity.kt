package com.chimy.challengevlue

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.chimy.challengevlue.ui.main.MapScreen
import com.chimy.challengevlue.ui.theme.ChallengeVlueTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChallengeVlueTheme {
                val context = LocalContext.current
                MapScreen(
                    context = context,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}