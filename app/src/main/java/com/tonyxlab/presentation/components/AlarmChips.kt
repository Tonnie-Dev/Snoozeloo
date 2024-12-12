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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.domain.model.AlarmItem
import com.tonyxlab.domain.model.DayChipState
import com.tonyxlab.presentation.ui.theme.LocalSpacing
import com.tonyxlab.presentation.ui.theme.SnoozelooTheme
import com.tonyxlab.utils.getRandomAlarmItem

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChipsRow(
   daysActive:List<DayChipState>,
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

       daysActive.forEachIndexed { i, state ->

            DayChip(
                    text = daysOfWeek[i],
                    onSelectChip = onDayChipClick,
                    selected = state.isEnabled,
                    modifier = Modifier.weight(1f)
            )

        }
    }
}

@Composable
fun DayChip(
    text: String,
    selected: Boolean,
    onSelectChip: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current
    var isSelected by remember { mutableStateOf(selected) }

    Box(
            modifier = modifier
                    .clip(RoundedCornerShape(spacing.spaceMedium + spacing.spaceExtraSmall))
                    .background(
                            color = if (isSelected)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.inversePrimary
                    )
                    .clickable {
                        onSelectChip()
                        isSelected = !isSelected
                    }
                    .padding(
                            end = spacing.spaceDoubleDp,
                            top = spacing.spaceExtraSmall,
                            bottom = spacing.spaceExtraSmall
                    ),
            contentAlignment = Alignment.Center
    ) {

        Text(
                text = text,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary
                else
                    MaterialTheme.colorScheme.onSurface
        )
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
                        onSelectChip = {},
                        selected = true,
                )

                DayChip(
                        text = "Tu",
                        onSelectChip = {},
                        selected = false,
                )
            }

        }
    }
}


@PreviewLightDark
@Composable
private fun WeekRowPreview() {

    SnoozelooTheme {

        Surface {

            ChipsRow(
                    onDayChipClick = {},
                    daysActive = getRandomAlarmItem().daysActive
            )
        }
    }
}
