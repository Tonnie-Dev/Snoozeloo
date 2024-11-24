package com.tonyxlab.presentation.home

import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.R
import com.tonyxlab.domain.model.AlarmItem
import com.tonyxlab.presentation.components.AlarmCard
import com.tonyxlab.presentation.ui.theme.LocalSpacing
import com.tonyxlab.presentation.ui.theme.SnoozelooTheme
import com.tonyxlab.utils.getRandomAlarmItems

@Composable
fun HomeScreen(
    items: List<AlarmItem>,
    onAlarmItemClick: (id: String) -> Unit,
    onClickAddItem: () -> Unit,
    modifier: Modifier = Modifier
) {

    val spacing = LocalSpacing.current
    Scaffold(floatingActionButton = {

        FloatingActionButton(
                modifier = Modifier
                        .clip(CircleShape)
                        .size(spacing.spaceExtraLarge), onClick = onClickAddItem
        ) {
            Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_alarm_item_text)
            )
        }

    }, floatingActionButtonPosition = FabPosition.Center) { innerPadding ->

        items.ifEmpty { EmptyScreen() }

        HomeScreenContent(
                modifier = modifier.padding(paddingValues = innerPadding),
                onAlarmItemClick = onAlarmItemClick,
                items = items
        )
    }
}

@Composable
fun HomeScreenContent(
    items: List<AlarmItem>,
    onAlarmItemClick: (id: String) -> Unit,
    modifier: Modifier = Modifier
) {

    val spacing = LocalSpacing.current
    LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(spacing.spaceMedium)
    ) {

        items(items = items, key = { it.id }) {


            AlarmCard(alarmItem = it, onAlarmItemClick = onAlarmItemClick, onDayChipClick = {})
        }
    }
}

@PreviewLightDark
@Composable
private fun EmptyHomeScreenPreview() {
    SnoozelooTheme {

        Surface {

            HomeScreen(
                    items = emptyList(),
                    onAlarmItemClick = {},
                    onClickAddItem = {}
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun FilledHomeScreenPreview() {
    SnoozelooTheme {
        HomeScreen(
                items = getRandomAlarmItems(10),
                onAlarmItemClick = {},
                onClickAddItem = {}
        )

    }
}
