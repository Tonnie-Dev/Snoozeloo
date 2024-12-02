package com.tonyxlab.presentation.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.R
import com.tonyxlab.domain.model.AlarmItem
import com.tonyxlab.presentation.ui.theme.LocalSpacing
import com.tonyxlab.presentation.ui.theme.SnoozelooTheme
import com.tonyxlab.utils.alarmIn
import com.tonyxlab.utils.getAmPmSuffix
import com.tonyxlab.utils.getBlack_900
import com.tonyxlab.utils.getRandomAlarmItem
import com.tonyxlab.utils.removeAmPmSuffix
import com.tonyxlab.utils.toAmPmTime

@Composable
fun AlarmCard(
    alarmItem: AlarmItem,
    onAlarmItemClick: (id: String) -> Unit,
    onDayChipClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val spacing = LocalSpacing.current

    Column(
            modifier = modifier
                    .clip(RoundedCornerShape(spacing.spaceSmall))
                    .fillMaxWidth()
                    .clickable { onAlarmItemClick(alarmItem.id) }
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(spacing.spaceMedium)
    ) {

        AlarmFace(alarmItem = alarmItem)
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        ChipsRow(
                alarmItem = alarmItem,
                onDayChipClick = onDayChipClick
        )
    }
}


@Composable
fun AlarmFace(
    alarmItem: AlarmItem,
    modifier: Modifier = Modifier
) {

    val spacing = LocalSpacing.current
    Column(modifier = modifier.fillMaxWidth()) {

        Row(
                modifier = Modifier.fillMaxWidth(),
                Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                    text = alarmItem.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = getBlack_900(),
                    fontWeight = FontWeight.W600
            )


            Switch(checked = true, onCheckedChange = {})
        }

        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        Row(modifier = Modifier.fillMaxWidth()) {

            Text(
                    modifier = Modifier.alignByBaseline(),
                    text = alarmItem.triggerTime.toAmPmTime()
                            .removeAmPmSuffix(),
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.W500,
                    color = getBlack_900()
            )

            Text(
                    modifier = Modifier.alignByBaseline(),
                    text = alarmItem.triggerTime.toAmPmTime()
                            .getAmPmSuffix(),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.W500,
                    color = getBlack_900()
            )

        }

        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                    text = stringResource(
                            id = R.string.alarm_in_text,
                            alarmItem.durationToNextTrigger.alarmIn()
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.W500,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = .7f)
            )
        }
    }
}


@PreviewLightDark
@Composable
private fun AlarmFacePreview() {

    SnoozelooTheme {

        Surface {

            AlarmFace(alarmItem = getRandomAlarmItem())

        }
    }


}

@PreviewLightDark
@Composable
private fun AlarmCardPreview() {
    SnoozelooTheme {
        Surface {


            AlarmCard(
                    alarmItem = getRandomAlarmItem(),
                    onAlarmItemClick = {},
                    onDayChipClick = {}
            )

        }
    }
}