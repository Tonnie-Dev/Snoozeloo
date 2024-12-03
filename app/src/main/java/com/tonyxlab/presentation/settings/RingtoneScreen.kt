package com.tonyxlab.presentation.settings

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import com.tonyxlab.R
import com.tonyxlab.domain.model.Ringtone
import com.tonyxlab.presentation.components.CircularCheckbox
import com.tonyxlab.presentation.ui.theme.LocalSpacing
import com.tonyxlab.presentation.ui.theme.SnoozelooTheme


@Composable
fun RingtoneScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val ringtones by viewModel.ringtones.collectAsState()
    RingtoneScreenContent(ringtones = ringtones)
}

@Composable
fun RingtoneScreenContent(
    ringtones: List<Ringtone>,
    modifier: Modifier = Modifier
) {

    val spacing = LocalSpacing.current
    Scaffold { innerPadding ->

        LazyColumn(modifier = Modifier.padding(innerPadding), contentPadding = PaddingValues( horizontal = spacing.spaceMedium)) {

            item {

                RingtoneRow(
                        modifier = Modifier.padding(spacing.spaceSmall),
                        ringtoneTitle = "Silent",
                        isChecked = false,
                        onCheckChange = {},
                        icon = painterResource(R.drawable.notification_silent)
                )
            }
            items(items = ringtones, key = { it.ringtoneName }) {

                RingtoneRow(
                        modifier = Modifier.padding(spacing.spaceSmall),
                        ringtoneTitle = it.ringtoneName,
                        isChecked = true,
                        onCheckChange = {},
                        icon = painterResource(R.drawable.notification_ringing)
                )
            }

        }

    }
}

@Composable
fun RingtoneRow(
    ringtoneTitle: String,
    isChecked: Boolean,
    onCheckChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    icon: Painter
) {

    val spacing = LocalSpacing.current

    Row(
            modifier = modifier
                    .clip(RoundedCornerShape(spacing.spaceSmall))
                    .background(MaterialTheme.colorScheme.surface)
                    .fillMaxWidth()
                    .padding(
                            vertical = spacing.spaceMedium,
                            horizontal = spacing.spaceMedium
                    )
                    ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(modifier = Modifier.padding(

        ),verticalAlignment = Alignment.CenterVertically) {

            Box(modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.background)
                    .padding(spacing.spaceSmall),contentAlignment = Alignment.Center){


                Icon(
                        modifier = Modifier

                                .padding(spacing.spaceSmall),
                        painter = painterResource(R.drawable.notification_ringing),
                        contentDescription = ""
                )
            }

            Spacer(modifier = Modifier.width(spacing.spaceSmall))

            Text(
                    text = ringtoneTitle,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.W600
            )
        }

        CircularCheckbox(
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
                        icon = painterResource(R.drawable.notification_ringing)
                )
                RingtoneRow(
                        ringtoneTitle = "Blue Hills",
                        isChecked = false,
                        onCheckChange = {},
                        icon = painterResource(R.drawable.notification_silent)
                )
            }
        }


    }

}

@PreviewLightDark
@Composable
private fun RingtoneContentPreview() {

    SnoozelooTheme {

        RingtoneScreenContent(ringtones = getRandomRingtones())
    }
}

fun getRandomRingtones(count: Int = 18): List<Ringtone> {

    val ringtoneTitles = listOf("Blue Hills", "Red Hills")

    return buildList {

        repeat(count) {

            val ringtoneTitle = if (it % 2 == 0)
                ringtoneTitles[0]
            else
                ringtoneTitles[1]

            add(
                    Ringtone(
                            ringtoneName = "$ringtoneTitle ${it +1}",
                            ringtoneUri = Uri.parse(ringtoneTitle)
                    )
            )

        }
    }

}