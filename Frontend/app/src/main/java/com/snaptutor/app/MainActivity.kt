package com.snaptutor.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.snaptutor.app.core.di.DefaultAppContainer
import com.snaptutor.app.core.navigation.AppNavigation
import com.snaptutor.app.core.ui.theme.SnapTutorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val appContainer = (application as? SnapTutorApplication)?.container
            ?: DefaultAppContainer()

        setContent {
            SnapTutorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(container = appContainer)
                }
            }
        }
    }
}
