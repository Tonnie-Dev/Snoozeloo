package com.tonyxlab.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.domain.model.AlarmItem
import com.tonyxlab.presentation.ui.theme.LocalSpacing
import com.tonyxlab.presentation.ui.theme.SnoozelooTheme
import com.tonyxlab.utils.getBlue_100
import com.tonyxlab.utils.getBlue_600
import com.tonyxlab.utils.getRandomAlarmItem

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChipsRow(
    alarmItem: AlarmItem,
    onDayChipClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current
    val daysOfWeek = listOf("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su")

    FlowRow(
            modifier = modifier
                    .fillMaxWidth(),
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

            ChipsRow(onDayChipClick = {}, alarmItem = getRandomAlarmItem())
        }
    }
}


@Composable
fun DayChip(
    text: String,
    onClick: () -> Unit,
    isSelected: Boolean,
    activeColor: Color = getBlue_600(),
    inactiveColor: Color = getBlue_100(),
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current

    Box(
            modifier = modifier
                    .clip(RoundedCornerShape(spacing.spaceMedium + spacing.spaceExtraSmall))
                    .background(color = if (isSelected) activeColor else inactiveColor)
                    .clickable { onClick() }
                    .padding(end = spacing.spaceDoubleDp, top = spacing.spaceExtraSmall, bottom = spacing.spaceExtraSmall),
            contentAlignment = Alignment.Center
    ) {

        Text(text = text)
    }
}


@PreviewLightDark
@Composable
private fun DayChipPreview() {

    val spacing = LocalSpacing.current
    SnoozelooTheme {

        Surface {
            Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(spacing.spaceExtraLarge)
            ) {
                DayChip(
                        text = "Tu",
                        onClick = {},
                        isSelected = true,
                )

                DayChip(
                        text = "Tu",
                        onClick = {},
                        isSelected = false,
                )
            }

        }
    }
}
