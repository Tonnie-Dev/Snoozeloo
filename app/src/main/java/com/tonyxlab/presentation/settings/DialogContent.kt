package com.tonyxlab.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.tonyxlab.R
import com.tonyxlab.domain.model.AlarmItem
import com.tonyxlab.presentation.components.MediumButton
import com.tonyxlab.presentation.components.SmallButton
import com.tonyxlab.presentation.ui.theme.LocalSpacing
import com.tonyxlab.presentation.ui.theme.SnoozelooTheme
import com.tonyxlab.utils.alarmIn
import com.tonyxlab.utils.getHourString
import com.tonyxlab.utils.getMinuteString
import com.tonyxlab.utils.getRandomAlarmItem

@Composable
fun SettingsScreenContent(
    alarmItem: AlarmItem,
    onClose: () -> Unit,
    onSave: () -> Unit,
    isCloseButtonEnabled: Boolean,
    isSaveButtonEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current
    Column(modifier = modifier) {

        Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SmallButton(
                    icon = Icons.Default.Close,
                    onClickIcon = onClose,
                    isEnabled = isCloseButtonEnabled,
                    contentDescription = stringResource(R.string.close_window_text),
            )

            MediumButton(
                    text = stringResource(id = R.string.save_text),
                    onClick = onSave,
                    isEnabled = isSaveButtonEnabled
            )
        }

        TimePanel(
                alarmItem = alarmItem,
                onHourBoxClick = {},
                onMinuteBoxClick = {}
        )
    }
}


@Composable
fun TimePanel(
    alarmItem: AlarmItem,
    onHourBoxClick: () -> Unit,
    onMinuteBoxClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current
    Surface(
            modifier = modifier.padding(spacing.spaceMedium),
            shape = RoundedCornerShape(spacing.spaceMedium)
    ) {

        Column {

            Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
            ) {

                Card(
                        modifier = Modifier.padding(
                                horizontal = spacing.spaceDefault,
                                vertical = spacing.spaceDefault
                        ),
                        shape = RoundedCornerShape(spacing.spaceDoubleDp * 5)
                ) {
                    Text(
                            modifier = Modifier.padding(
                                    horizontal = spacing.spaceLarge,
                                    vertical = spacing.spaceMedium
                            ),
                            text = alarmItem.triggerTime.getHourString(),
                            style = MaterialTheme.typography.displayLarge,
                            fontWeight = FontWeight.W500
                    )
                }
                Spacer(modifier = Modifier.width(spacing.spaceMedium))

                Text(
                        text = ":",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(spacing.spaceMedium))
                Card(
                        modifier = Modifier.padding(
                                horizontal = spacing.spaceDefault,
                                vertical = spacing.spaceDefault
                        ),
                        shape = RoundedCornerShape(spacing.spaceDoubleDp * 5)
                ) {
                    Text(
                            modifier = Modifier.padding(
                                    horizontal = spacing.spaceLarge,
                                    vertical = spacing.spaceMedium
                            ),
                            text = alarmItem.triggerTime.getMinuteString(),
                            style = MaterialTheme.typography.displayLarge,
                            fontWeight = FontWeight.W500
                    )
                }
            }

            Spacer(modifier = Modifier.height(spacing.spaceMedium))


            Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(
                            id = R.string.alarm_in_text,
                            alarmItem.durationToNextTrigger.alarmIn()
                    ),
                    textAlign = TextAlign.Center
            )
        }


    }
}


@Composable
fun TextPanel(
    text1: String,
    text2: String,
    modifier: Modifier = Modifier
) {

    val spacing = LocalSpacing.current

    Row(
            modifier = modifier
                    .fillMaxWidth()
                    .padding(spacing.spaceMedium),
            Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
                text = text1,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.W600
        )

        Text(
                text = text2,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.W600
        )
    }
}


@Preview(showBackground = true)
@PreviewLightDark
@Composable
private fun SettingsScreenContentPreview() {

    SnoozelooTheme {

        Surface {

            SettingsScreenContent(
                    alarmItem = getRandomAlarmItem(),
                    onClose = {},
                    onSave = {},
                    isCloseButtonEnabled = false,
                    isSaveButtonEnabled = false,
                    modifier = Modifier.fillMaxSize()
            )
        }
    }
}