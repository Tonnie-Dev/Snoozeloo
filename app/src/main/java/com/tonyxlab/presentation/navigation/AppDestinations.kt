package com.tonyxlab.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.tonyxlab.presentation.home.HomeScreen
import com.tonyxlab.presentation.settings.RingtoneScreen
import com.tonyxlab.presentation.settings.SettingsScreen
import com.tonyxlab.presentation.settings.SettingsViewModel
import com.tonyxlab.utils.getRandomAlarmItem
import com.tonyxlab.utils.getRandomAlarmItems
import kotlinx.serialization.Serializable

fun NavGraphBuilder.appDestinations(navController: NavController) {

    composable<HomeScreenObject> {

        HomeScreen(
                items = getRandomAlarmItems(),
                onAlarmItemClick = { navController.navigate(NestedSettingsScreens) },
                onClickAddItem = {}
        )
    }
    navigation <NestedSettingsScreens>(startDestination = SettingsScreenObject) {
        composable<SettingsScreenObject> {
            val viewModel: SettingsViewModel = hiltViewModel(navController.getBackStackEntry(NestedSettingsScreens))


            SettingsScreen(
                    alarmItem = getRandomAlarmItem(),
                    onClose = { navController.navigate(route = HomeScreenObject) },
                    onSave = {},
                    onDayChipClick = {},
                    isSaveButtonEnabled = false,
                    onSelectRingtone = { navController.navigate(route = RingtoneScreenObject) },
                    viewModel = viewModel
            )
        }
        composable<RingtoneScreenObject> {


            val viewModel: SettingsViewModel = hiltViewModel(navController.getBackStackEntry(NestedSettingsScreens))
            RingtoneScreen(
                    onCloseWindow = { navController.navigate(route = SettingsScreenObject) },
                    viewModel = viewModel
            )

        }
    }

}


@Serializable
data object HomeScreenObject

@Serializable
data object NestedSettingsScreens

@Serializable
data object SettingsScreenObject

@Serializable
data object RingtoneScreenObject
