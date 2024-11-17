package com.tonyxlab.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.domain.model.AlarmItem
import com.tonyxlab.domain.model.DayChipState
import com.tonyxlab.presentation.ui.theme.LocalSpacing
import com.tonyxlab.presentation.ui.theme.SnoozelooTheme
import com.tonyxlab.utils.getBlue_600
import com.tonyxlab.utils.now
import kotlinx.datetime.LocalDateTime
import java.util.UUID

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WeekRow(
    alarmItem: AlarmItem,
    onDayChipClick: ()-> Unit,
    modifier: Modifier = Modifier
) {
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


private fun getRandomAlarmItem(): AlarmItem = AlarmItem(
        id = UUID.randomUUID()
                .toString(),
        isEnabled = false,
        name = "Alarm",
        triggerTime = LocalDateTime.now(),
        durationToNextTrigger = 0L,
        daysActive = populateDaysActiveList(),
)

private fun populateDaysActiveList(): List<DayChipState> {

    val daysOfWeek = listOf("Su", "Mo", "Tu", "We", "Th", "Fr", "Sa")
    return buildList {

        (0..6).forEach { i ->

            add(DayChipState(day = daysOfWeek[i], isEnabled = i % 2 == 0))
        }
    }
}