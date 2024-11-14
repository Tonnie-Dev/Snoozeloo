package com.tonyxlab.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.domain.model.AlarmItem
import com.tonyxlab.domain.model.DayChipState
import com.tonyxlab.presentation.ui.theme.LocalSpacing
import com.tonyxlab.presentation.ui.theme.SnoozelooTheme
import com.tonyxlab.utils.getRoyalBlueComposeColor
import java.time.LocalDateTime
import java.util.UUID

@Composable
fun WeekRow(
    onClick: () -> Unit,
    alarmItem: AlarmItem,
    activeColor: Color = getRoyalBlueComposeColor(),
    /*inactiveColor: Color,*/
    modifier: Modifier = Modifier
) {
    val daysOfWeek = listOf("Su", "Mo", "Tu", "We", "Th", "Fr", "Sa")
    val spacing = LocalSpacing.current
    Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
    ) {


        alarmItem.daysActive.forEachIndexed { i, state ->

            FilterChip(
                    selected = state.isEnabled,
                    onClick = onClick,
                    shape = RoundedCornerShape(spacing.spaceSmall),
                    label = { Text(text = daysOfWeek[i]) }
            )
        }
    }
}


@PreviewLightDark
@Composable
private fun Preview() {

    SnoozelooTheme {

        Surface {

            WeekRow(onClick ={}, alarmItem = getRandomAlarmItem())
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

private fun populateDaysActiveList():List<DayChipState> {

    val daysOfWeek = listOf("Su", "Mo", "Tu", "We", "Th", "Fr", "Sa")
    return  buildList {

        (0..6).forEach{ i ->

            add(DayChipState(day = daysOfWeek[i], isEnabled = i%2 ==0 ))
        }
    }
}