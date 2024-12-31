package com.tonyxlab.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import com.tonyxlab.R
import com.tonyxlab.domain.model.AlarmItem
import com.tonyxlab.presentation.components.AlarmCard
import com.tonyxlab.presentation.ui.theme.LocalSpacing
import com.tonyxlab.presentation.ui.theme.SnoozelooTheme

@Composable
fun HomeScreen(
    onAddNewAlarm: () -> Unit,
    onAlarmItemClick: (String?) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val spacing = LocalSpacing.current
    val alarmItems by viewModel.alarms.collectAsState()

    HomeScreenContent(
            modifier = modifier,
            alarmItems = alarmItems,
            onAlarmItemClick = onAlarmItemClick,
            onAddNewAlarm = onAddNewAlarm,
            onChangeDayAlarmActivityState = viewModel::onDayAlarmActivityChange
    )
}

@Composable
fun HomeScreenContent(
    alarmItems: List<AlarmState>,
    onAlarmItemClick: (id: String) -> Unit,
    onChangeDayAlarmActivityState:(AlarmItem, Int) -> Unit,
    onAddNewAlarm: () -> Unit,
    modifier: Modifier = Modifier
) {

    val spacing = LocalSpacing.current


    Scaffold(
            floatingActionButton = {

                FloatingActionButton(
                        modifier = Modifier
                                .clip(CircleShape)
                                .size(spacing.spaceExtraLarge),
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        onClick = onAddNewAlarm
                ) {
                    Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.add_alarm_item_text)
                    )
                }

            },
            floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->

        if (alarmItems.isEmpty()) {

            EmptyScreen(modifier = modifier.padding(spacing.spaceMedium))

        }

        LazyColumn(
                modifier = modifier
                        .padding(innerPadding)
                        .padding(spacing.spaceMedium),
                contentPadding = innerPadding
        ) {

            item {
                Text(
                        stringResource(R.string.your_alarms_header_text),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.W500
                )
            }
            items(items = alarmItems, key = { it.alarmItem.id }) {

                AlarmCard(modifier = Modifier.padding(spacing.spaceSmall),
                        alarmItem = it,
                        onAlarmItemClick = onAlarmItemClick,
                        onDayChipClick = onChangeDayAlarmActivityState)
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun EmptyHomeScreenPreview() {
    SnoozelooTheme {

        Surface {

            HomeScreenContent(
                    onAddNewAlarm = {},
                    alarmItems = emptyList(),
                    onAlarmItemClick = {},
                    modifier = Modifier.fillMaxSize(),
                    onChangeDayAlarmActivityState = { _, _ ->}
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun FilledHomeScreenPreview() {
    SnoozelooTheme {

        HomeScreenContent(
                onAddNewAlarm = {},
                alarmItems = emptyList(),
                onAlarmItemClick = {},
                modifier = Modifier.fillMaxSize(),
                onChangeDayAlarmActivityState = { _, _ ->}
        )

    }
}

