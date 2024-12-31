package com.tonyxlab.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.tonyxlab.presentation.navigation.HomeScreenObject
import com.tonyxlab.presentation.navigation.appDestinations
import com.tonyxlab.presentation.ui.theme.SnoozelooTheme
import com.tonyxlab.utils.zoomSplashAnimation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainScreenViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        installSplashScreen().apply {

            zoomSplashAnimation(viewModel.launchApp.value)

        }

        setContent {
            SnoozelooTheme {
                val navController = rememberNavController()

                NavHost(
                        navController = navController,
                        startDestination = HomeScreenObject
                ) {

                    appDestinations(navController)
                }
            }
        }
    }
}

