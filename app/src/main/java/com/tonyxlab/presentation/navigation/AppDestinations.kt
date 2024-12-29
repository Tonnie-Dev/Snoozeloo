package com.tonyxlab.presentation.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.tonyxlab.presentation.home.HomeScreen
import com.tonyxlab.presentation.settings.RingtoneScreen
import com.tonyxlab.presentation.settings.SettingsScreen
import com.tonyxlab.presentation.settings.SettingsViewModel
import kotlinx.serialization.Serializable

fun NavGraphBuilder.appDestinations(navController: NavController) {

    composable<HomeScreenObject> {

        HomeScreen(
                onAlarmItemClick = { itemId ->
                    navController.navigate(NestedScreens(id = itemId))

                },
                onAddNewAlarm = { navController.navigate(NestedScreens()) },
        )
    }

    navigation<NestedScreens>(startDestination = SettingsScreenObject) {

        composable<SettingsScreenObject> { backStackEntry ->

            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry<NestedScreens>()
            }

            val viewModel: SettingsViewModel = hiltViewModel(parentEntry)

            SettingsScreen(
                    onClose = { navController.navigate(route = HomeScreenObject) },
                    onSelectRingtone = { navController.navigate(route = RingtoneScreenObject) },
                    viewModel = viewModel
            )
        }

        composable<RingtoneScreenObject> { backStackEntry ->

            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry<NestedScreens>()
            }

            val viewModel: SettingsViewModel = hiltViewModel(parentEntry)

            RingtoneScreen(
                    onCloseWindow = { navController.navigate(SettingsScreenObject) },
                    viewModel = viewModel
            )

        }
    }

}


@Serializable
data object HomeScreenObject

@Serializable

data class NestedScreens(val id: String? = null)

@Serializable
data object SettingsScreenObject

@Serializable
data object RingtoneScreenObject
