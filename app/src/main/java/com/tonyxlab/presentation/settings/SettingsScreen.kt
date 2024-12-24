package com.tonyxlab.presentation.settings

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import com.tonyxlab.R
import com.tonyxlab.domain.model.DayChipState
import com.tonyxlab.domain.model.Ringtone
import com.tonyxlab.domain.model.SILENT_RINGTONE
import com.tonyxlab.presentation.components.AppTopBar
import com.tonyxlab.presentation.components.ChipsRow
import com.tonyxlab.presentation.components.MediumButton
import com.tonyxlab.presentation.components.ModalDialog
import com.tonyxlab.presentation.components.NumberInputField
import com.tonyxlab.presentation.components.VolumeSlider
import com.tonyxlab.presentation.ui.theme.LocalSpacing
import com.tonyxlab.presentation.ui.theme.SnoozelooTheme
import com.tonyxlab.utils.TextFieldValue
import com.tonyxlab.utils.alarmIn


@Composable
fun SettingsScreen(
    onClose: () -> Unit,
    onDayChipClick: () -> Unit,
    onSelectRingtone: (String?) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current

    val uiState by viewModel.uiState.collectAsState()

    val hourFieldValue by viewModel.hourFieldValue.collectAsState()
    val minuteFieldValue by viewModel.minuteFieldValue.collectAsState()
    val nameFieldValue by viewModel.nameFieldValue.collectAsState()
    val volumeFieldValue by viewModel.volumeFieldValue.collectAsState()
    val hapticsFieldValue by viewModel.hapticsFieldValue.collectAsState()

    val selectedRingtone by viewModel.selectedRingtone.collectAsState()

    SettingsScreenContent(
            modifier = modifier.padding(spacing.spaceMedium),
            durationToNextTrigger = uiState.durationToNextTrigger,
            daysActive = uiState.daysActive,
            showAlarmIn = uiState.showAlarmIn,
            onClose = onClose,
            onSave = viewModel::onSaveButtonClick,
            onDayChipClick = onDayChipClick,
            isSaveButtonEnabled = uiState.isSaveEnabled,
            onSelectRingtone = onSelectRingtone,
            hourFieldValue = hourFieldValue,
            minuteFieldValue = minuteFieldValue,
            nameFieldValue = nameFieldValue,
            volumeFieldValue = volumeFieldValue,
            hapticsFieldValue = hapticsFieldValue,
            selectedRingtone = selectedRingtone,
            onDeleteAlarmNameText = viewModel::onDeleteAlarmText
    )
}


@Composable
fun SettingsScreenContent(
    durationToNextTrigger: Long,
    daysActive: List<DayChipState>,
    onClose: () -> Unit,
    onSave: () -> Unit,
    showAlarmIn: Boolean,
    hourFieldValue: TextFieldValue<String>,
    minuteFieldValue: TextFieldValue<String>,
    nameFieldValue: TextFieldValue<String>,
    volumeFieldValue: TextFieldValue<Float>,
    hapticsFieldValue: TextFieldValue<Boolean>,
    selectedRingtone: Ringtone,
    onDayChipClick: () -> Unit,
    isSaveButtonEnabled: Boolean,
    onSelectRingtone: (String) -> Unit,
    onDeleteAlarmNameText: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
            topBar = {

                AppTopBar(

                        isSmallButtonEnabled = true,
                        onClickSmallButton = onClose,
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
                    durationToNextTrigger = durationToNextTrigger,
                    hourFieldValue = hourFieldValue,
                    minuteFieldValue = minuteFieldValue,
                    showAlarmIn = showAlarmIn
            )

            //Alarm Name Setting
            TitlePanel(
                    mainText = stringResource(R.string.alarm_name_text),
                    subText = nameFieldValue.value,
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
                                daysActive = daysActive,
                                onDayChipClick = onDayChipClick
                        )
                    }
            )


            //Ringtone Setting
            TitlePanel(
                    mainText = stringResource(id = R.string.alarm_ringtone_text),
                    subText = selectedRingtone.ringtoneName,
                    sideContent = { text ->
                        text?.let {
                            Text(
                                    text = it,
                                    maxLines = 1,
                                    overflow = TextOverflow.Clip,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.W500
                            )
                        }
                    },
                    onClickComponent = {
                        onSelectRingtone(selectedRingtone.ringtoneName)
                    }

            )

            //Alarm Volume Setting
            TitlePanel(
                    mainText = stringResource(R.string.alarm_volume_text),
                    bottomContent = {
                        VolumeSlider(
                                value = volumeFieldValue.value,
                                onValueChange = volumeFieldValue.onValueChange
                        )
                    }
            )

            //Vibration Setting
            TitlePanel(
                    mainText = stringResource(R.string.vibrate_text),
                    sideContent = {
                        Switch(
                                checked = hapticsFieldValue.value,
                                onCheckedChange = hapticsFieldValue.onValueChange
                        )
                    }
            )

            // Modal Dialog
            ModalDialog(
                    title = stringResource(id = R.string.alarm_name_text),
                    isShowDialog = showDialog,
                    textValue = nameFieldValue.value,
                    onValueChange = nameFieldValue.onValueChange,
                    onConfirmDialog = {

                        showDialog = false

                    },
                    isDialogSaveButtonEnabled = nameFieldValue.isConfirmButtonEnabled,
                    onDeleteText = onDeleteAlarmNameText,
                    onDismissDialog = {
                        onDeleteAlarmNameText()
                        showDialog = false
                    }
            )

        }
    }
}


@Composable
fun TimePanel(
    durationToNextTrigger: Long,
    hourFieldValue: TextFieldValue<String>,
    minuteFieldValue: TextFieldValue<String>,
    showAlarmIn: Boolean,
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
                        value = hourFieldValue.value,
                        onValueChanged = hourFieldValue.onValueChange,
                        isError = hourFieldValue.isError
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
                        value = minuteFieldValue.value,
                        onValueChanged = minuteFieldValue.onValueChange,
                        isError = minuteFieldValue.isError

                )
            }
        }

        Spacer(modifier = Modifier.height(spacing.spaceMedium))

        AnimatedVisibility(visible = showAlarmIn) {


            Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(
                            id = R.string.alarm_in_text,
                            durationToNextTrigger.alarmIn()
                    ),
                    textAlign = TextAlign.Center
            )
        }
    }


}

@Composable
fun TitlePanel(
    mainText: String,
    modifier: Modifier = Modifier,
    subText: String? = null,
    onClickComponent: (() -> Unit)? = null,
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
            Spacer(modifier = Modifier.width(spacing.spaceSmall))
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
                durationToNextTrigger = 0L,
                onClose = {},
                onSave = {},
                onDayChipClick = {},
                isSaveButtonEnabled = false,
                onSelectRingtone = {},
                onDeleteAlarmNameText = {},
                hourFieldValue = TextFieldValue(
                        value = "13",
                        onValueChange = {},
                        isError = false
                ),
                minuteFieldValue = TextFieldValue(
                        value = "13",
                        onValueChange = {},
                        isError = false
                ),
                nameFieldValue = TextFieldValue(
                        value = "Wake Up",
                        onValueChange = {},
                        isError = false,
                        isConfirmButtonEnabled = false
                ),
                volumeFieldValue = TextFieldValue(
                        value = .7f,
                        onValueChange = {},
                        isError = false,
                        isConfirmButtonEnabled = false
                ),
                hapticsFieldValue = TextFieldValue(
                        value = true,
                        onValueChange = {},
                        isError = false,
                        isConfirmButtonEnabled = false
                ),
                daysActive = emptyList(),
                showAlarmIn = false,
                selectedRingtone = SILENT_RINGTONE,
        )
    }
}
