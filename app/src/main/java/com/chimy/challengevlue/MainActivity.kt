package com.chimy.challengevlue

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.chimy.challengevlue.ui.main.AppContent
import com.chimy.challengevlue.ui.theme.ChallengeVlueTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChallengeVlueTheme {
                AppContent()
            }
        }
    }
}