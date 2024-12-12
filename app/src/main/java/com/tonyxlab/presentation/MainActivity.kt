package com.tonyxlab.presentation

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.tonyxlab.domain.model.AlarmItem
import com.tonyxlab.presentation.home.HomeScreen
import com.tonyxlab.presentation.navigation.HomeScreenObject
import com.tonyxlab.presentation.navigation.appDestinations
import com.tonyxlab.presentation.settings.RingtoneScreen
import com.tonyxlab.presentation.settings.SettingsScreen
import com.tonyxlab.presentation.settings.SettingsScreenContent
import com.tonyxlab.presentation.settings.SettingsViewModel
import com.tonyxlab.presentation.ui.theme.SnoozelooTheme
import com.tonyxlab.utils.getRandomAlarmItem
import com.tonyxlab.utils.getRandomAlarmItems
import com.tonyxlab.utils.zoomSplashAnimation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

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
                ){

                    appDestinations(navController)
                }
        }
    }
}}

