package com.tonyxlab.presentation.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.domain.model.AlarmItem
import com.tonyxlab.presentation.ui.theme.LocalSpacing
import com.tonyxlab.presentation.ui.theme.SnoozelooTheme
import com.tonyxlab.utils.getRandomAlarmItem

@Composable
fun AlarmCard(
    alarmItem: AlarmItem,
    onAlarmItemClick: (id: String) -> Unit,
    onDayChipClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val spacing = LocalSpacing.current
    Surface(
            modifier = modifier
                    .fillMaxWidth().clickable {
                        onAlarmItemClick(alarmItem.id)
                    }
                    .padding(spacing.spaceMedium)
    ) {
        Column {


            DialFace(alarmItem = alarmItem)
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            ChipsRow(
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

        AlarmCard(
                alarmItem = getRandomAlarmItem(),
                onAlarmItemClick = {},
                onDayChipClick = {}
        )
    }
}