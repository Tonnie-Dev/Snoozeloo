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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tonyxlab.domain.model.AlarmItem
import com.tonyxlab.presentation.home.HomeScreen
import com.tonyxlab.presentation.settings.RingtoneScreen
import com.tonyxlab.presentation.settings.SettingsScreen
import com.tonyxlab.presentation.settings.SettingsScreenContent
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
                ) {

                    composable<HomeScreenObject> {

                        HomeScreen(
                                items = getRandomAlarmItems(),
                                onAlarmItemClick = { navController.navigate(SettingsScreenObject(it)) },
                                onClickAddItem = {}
                        )
                    }
                    composable<SettingsScreenObject> {

                        val args = it.id

                        SettingsScreen(
                                alarmItem = getRandomAlarmItem(),
                                onClose = {
                                    navController.popBackStack()
                                },
                                onSave = {},
                                onDayChipClick = {},
                                isSaveButtonEnabled = false,
                                onSelectRingtone = {
                                    navController.navigate(RingtoneScreenObject)
                                }
                        )

                    }

                    composable<RingtoneScreenObject> {

                        RingtoneScreen(onCloseWindow = { navController.popBackStack() })
                    }
                }
            }
        }
    }
}


@Serializable
object HomeScreenObject

@Serializable
data class SettingsScreenObject(val id: String?)

@Serializable
object RingtoneScreenObject