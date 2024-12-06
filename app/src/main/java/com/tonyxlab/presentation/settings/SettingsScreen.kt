package com.tonyxlab.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import com.tonyxlab.R
import com.tonyxlab.domain.model.AlarmItem
import com.tonyxlab.presentation.components.AppTopBar
import com.tonyxlab.presentation.components.ChipsRow
import com.tonyxlab.presentation.components.MediumButton
import com.tonyxlab.presentation.components.ModalDialog
import com.tonyxlab.presentation.components.NumberInputField
import com.tonyxlab.presentation.ui.theme.LocalSpacing
import com.tonyxlab.presentation.ui.theme.SnoozelooTheme
import com.tonyxlab.utils.alarmIn
import com.tonyxlab.utils.getHourString
import com.tonyxlab.utils.getMinuteString
import com.tonyxlab.utils.getRandomAlarmItem

@Composable
fun SettingsScreen(
    alarmItem: AlarmItem,
    onSave: () -> Unit,
    onClose: () -> Unit,
    volume: Float,
    onDayChipClick: () -> Unit,
    isCloseButtonEnabled: Boolean,
    isSaveButtonEnabled: Boolean,
    isVibrationEnabled: Boolean,
    onVibrationModeChange: (Boolean) -> Unit,
    onSelectRingtone: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current


    SettingsScreenContent(
            modifier = modifier.padding(spacing.spaceMedium),
            alarmItem = alarmItem,
            onClose = onClose,
            onSave = onSave,
            volume = volume,
            onDayChipClick = onDayChipClick,
            isCloseButtonEnabled = isCloseButtonEnabled,
            isSaveButtonEnabled = isSaveButtonEnabled,
            isVibrationEnabled = isVibrationEnabled,
            onVibrationModeChange = onVibrationModeChange,
            onSelectRingtone = onSelectRingtone
    )
}


@Composable
fun SettingsScreenContent(
    alarmItem: AlarmItem,
    onClose: () -> Unit,
    onSave: () -> Unit,
    volume: Float,
    onDayChipClick: () -> Unit,
    isCloseButtonEnabled: Boolean,
    isSaveButtonEnabled: Boolean,
    isVibrationEnabled: Boolean,
    onVibrationModeChange: (Boolean) -> Unit,
    onSelectRingtone: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
            topBar = {

                AppTopBar(

                        isSmallButtonEnabled = isCloseButtonEnabled,
                        onClickSmallButton = onSave,
                        smallButtonIcon = Icons.Default.Close,

                        mediumButton = {
                            MediumButton(
                                    text = stringResource(id = R.string.save_text),
                                    onClick = onSave,
                                    isEnabled = isSaveButtonEnabled
                            )
                        }
                )
            }) { innerPadding ->

        Column(
                modifier = modifier
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
        ) {


            //Set Time Setting
            TimePanel(
                    alarmItem = alarmItem,
                    onHourBoxClick = {},
                    onMinuteBoxClick = {}
            )

            //Alarm Name Setting
            TitlePanel(
                    mainText = stringResource(R.string.alarm_name_text),
                    subText = alarmItem.name,
                    sideContent = { text ->

                        text?.let {
                            Text(
                                    text = it,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.W500
                            )
                        }


                    },
                    onClickComponent = { showDialog = true }
            )

            //Chips-Day Setting
            TitlePanel(
                    mainText = stringResource(R.string.repeat_text),
                    bottomContent = {
                        ChipsRow(
                                modifier = Modifier,
                                alarmItem = alarmItem,
                                onDayChipClick = onDayChipClick
                        )
                    }
            )


            //Ringtone Setting
            TitlePanel(
                    mainText = stringResource(id = R.string.alarm_ringtone_text),
                    subText = "Default", // TODO: Fix Alarm Ringtone Name
                    sideContent = { text ->

                        text?.let {
                            Text(
                                    text = it,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.W500
                            )
                        }
                    },
                    onClickComponent = onSelectRingtone


            )

            //Alarm Volume Setting
            TitlePanel(
                    mainText = stringResource(R.string.alarm_volume_text),
                    bottomContent = { Slider(value = volume, onValueChange = {}) }
            )

            //Vibration Setting
            TitlePanel(
                    mainText = stringResource(R.string.vibrate_text),
                    sideContent = {
                        Switch(
                                checked = isVibrationEnabled,
                                onCheckedChange = onVibrationModeChange
                        )
                    }
            )

            // Modal Dialog
            ModalDialog(
                    title = stringResource(id = R.string.alarm_name_text),
                    isShowDialog = showDialog,
                    onConfirmDialog = {
                        // TODO: Call Save Name Here - from view model
                        showDialog = false
                    },
                    onDismissDialog = { showDialog = false })

        }
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
    Column(

            modifier = modifier
                    .clip(RoundedCornerShape(spacing.spaceMedium))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(spacing.spaceMedium),

            ) {

        Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
        ) {

            Card(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.background
                    ),
                    shape = RoundedCornerShape(spacing.spaceDoubleDp * 5)
            ) {

                NumberInputField(
                        modifier = Modifier.wrapContentSize(),
                        initialText = alarmItem.triggerTime.getHourString(),
                        range = (0..23)
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
                    colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.background
                    ),
                    shape = RoundedCornerShape(spacing.spaceDoubleDp * 5)
            ) {
                NumberInputField(
                        modifier = Modifier.wrapContentSize(),
                        initialText = alarmItem.triggerTime.getMinuteString(),
                        range = (0..59)
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


@Composable
fun TitlePanel(
    mainText: String,
    modifier: Modifier = Modifier,
    subText: String? = null,
    onClickComponent: (() -> Unit)? = null,
    onClickChip: (() -> Unit)? = null,
    onSelectRingtone: (() -> Unit)? = null,
    onAdjustVolume: (() -> Unit)? = null,
    onToggleVibration: (() -> Unit)? = null,
    sideContent: @Composable ((text: String?) -> Unit)? = null,
    bottomContent: @Composable (() -> Unit)? = null,


    ) {

    val spacing = LocalSpacing.current


    Column(
            modifier = Modifier
                    .clip(RoundedCornerShape(spacing.spaceDoubleDp * 2))
                    .background(color = MaterialTheme.colorScheme.surface)
                    .clickable { onClickComponent?.invoke() }
                    .padding(spacing.spaceMedium),
            verticalArrangement = Arrangement.spacedBy(spacing.spaceDoubleDp * 2)
    ) {

        Row(
                modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = spacing.spaceSmall),
                Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                    text = mainText,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.W600
            )

            sideContent?.invoke(subText)


        }
        bottomContent?.invoke()
    }

}


@PreviewLightDark
@Composable
private fun SettingsScreenContentPreview() {

    SnoozelooTheme {
        SettingsScreenContent(
                modifier = Modifier.fillMaxSize(),
                alarmItem = getRandomAlarmItem(),
                onClose = {},
                onSave = {},
                onDayChipClick = {},
                isCloseButtonEnabled = false,
                isSaveButtonEnabled = false,
                volume = .7f,
                isVibrationEnabled = false,
                onVibrationModeChange = {},
                onSelectRingtone = {}
        )
    }
}
