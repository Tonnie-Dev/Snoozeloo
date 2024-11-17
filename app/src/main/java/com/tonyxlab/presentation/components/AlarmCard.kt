package com.tonyxlab.presentation.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.domain.model.AlarmItem
import com.tonyxlab.presentation.ui.theme.LocalSpacing
import com.tonyxlab.presentation.ui.theme.SnoozelooTheme
import com.tonyxlab.utils.getRandomAlarmItem

@Composable
fun AlarmCard(
    alarmItem: AlarmItem,
    onDayChipClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val spacing = LocalSpacing.current
    Surface(
            modifier = modifier
                    .fillMaxWidth()
                    .padding(spacing.spaceMedium)
    ) {
        Column {


            DialFace(alarmItem = alarmItem)
            WeekRow(
                    alarmItem = alarmItem,
                    onDayChipClick = onDayChipClick
            )
        }
    }
}


@PreviewLightDark
@Composable
private fun AlarmCardPreview() {
    SnoozelooTheme {

        AlarmCard(alarmItem = getRandomAlarmItem(), onDayChipClick = {})
    }
}