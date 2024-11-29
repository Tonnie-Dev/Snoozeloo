package com.tonyxlab.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.presentation.ui.theme.LocalSpacing
import com.tonyxlab.presentation.ui.theme.SnoozelooTheme

@Composable
fun RingtoneRow(
    ringtoneTitle: String,
    isChecked: Boolean,
    onCheckChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Default.Notifications
) {

    val spacing = LocalSpacing.current

    Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {

            Icon(
                    modifier = Modifier
                            .size(spacing.spaceLarge)
                            .padding(spacing.spaceSmall),
                    imageVector = icon,
                    contentDescription = ""
            )

            Spacer(modifier = Modifier.width(spacing.spaceExtraSmall))

            Text(
                    text = ringtoneTitle,
                    style = MaterialTheme.typography.labelLarge
            )
        }

        Checkbox(
                modifier = Modifier.clip(CircleShape),
                checked = isChecked,
                onCheckedChange = onCheckChange
        )
    }
}


@PreviewLightDark
@Composable
private fun RingtoneRowPreview() {
    SnoozelooTheme {


        Surface {
            Column {

                RingtoneRow(
                        ringtoneTitle = "Blue Hills",
                        isChecked = true,
                        onCheckChange = {},
                )
                RingtoneRow(
                        ringtoneTitle = "Blue Hills",
                        isChecked = false,
                        onCheckChange = {},
                )
            }
        }


    }

}