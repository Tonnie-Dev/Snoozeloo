package com.tonyxlab.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.tonyxlab.domain.model.AlarmItem
import com.tonyxlab.presentation.ui.theme.LocalSpacing
import com.tonyxlab.utils.getAmPmSuffix
import com.tonyxlab.utils.removeAmPmSuffix
import com.tonyxlab.utils.toAmPmTime

@Composable
fun DialFace(alarmItem: AlarmItem, modifier: Modifier = Modifier) {

    val spacing = LocalSpacing.current
    Column(modifier = modifier.fillMaxWidth()) {

        Row(
                modifier = Modifier.fillMaxWidth(),
                Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = alarmItem.name, style = MaterialTheme.typography.titleSmall)
            IconToggleButton(checked = alarmItem.isEnabled, onCheckedChange = {}) { }
        }

        Row(modifier = Modifier.fillMaxWidth()) {

            Text(
                    text = alarmItem.triggerTime.toAmPmTime()
                            .removeAmPmSuffix(),
                    style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.width(spacing.spaceExtraSmall))
            Text(
                    text = alarmItem.triggerTime.toAmPmTime()
                            .getAmPmSuffix(),
                    style = MaterialTheme.typography.titleMedium
            )

        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = alarmItem.durationToNextTrigger)
        }
    }
}