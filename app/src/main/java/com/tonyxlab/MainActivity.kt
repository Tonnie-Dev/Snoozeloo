package com.tonyxlab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.tonyxlab.ui.theme.SnoozelooTheme
import com.tonyxlab.utils.zoomSplashAnimation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainScreenViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen().apply {

            zoomSplashAnimation(viewModel.launchApp.value)

        }


        setContent {
            SnoozelooTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    Text(text = "Tonnie", modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
