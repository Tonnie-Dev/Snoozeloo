package com.tonyxlab.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.domain.model.AlarmItem
import com.tonyxlab.presentation.ui.theme.LocalSpacing
import com.tonyxlab.presentation.ui.theme.SnoozelooTheme
import com.tonyxlab.utils.getRandomAlarmItem

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WeekRow(
    alarmItem: AlarmItem,
    onDayChipClick: ()-> Unit,
    modifier: Modifier = Modifier
)                    {
    val spacing = LocalSpacing.current
    val daysOfWeek = listOf("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su")

    FlowRow(
            modifier = modifier
                    .fillMaxWidth()
                    .padding(spacing.spaceMedium),
            maxLines = 1,
            horizontalArrangement = Arrangement.spacedBy(spacing.spaceExtraSmall)
    ) {

        alarmItem.daysActive.forEachIndexed { i, state ->

            DayChip(
                    text = daysOfWeek[i],
                    onClick = onDayChipClick,
                    isSelected = state.isEnabled,
                    modifier = Modifier.weight(1f)
            )

        }
    }
}

@PreviewLightDark
@Composable
private fun WeekRowPreview() {

    SnoozelooTheme {

        Surface {

            WeekRow(onDayChipClick = {}, alarmItem = getRandomAlarmItem())
        }
    }
}

