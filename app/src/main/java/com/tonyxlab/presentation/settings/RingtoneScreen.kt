package com.tonyxlab.presentation.settings

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.traceEventEnd
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
    val isPlaying by viewModel.isPlaying.collectAsState()
    RingtoneScreenContent(
            modifier = modifier,
            ringtones = ringtones,
            isPlaying = isPlaying,
            onPlayRingtone = viewModel::play,
            onStopPlay = viewModel::stop

    )
}

@Composable
fun RingtoneScreenContent(
    ringtones: List<Ringtone>,
    onPlayRingtone: (Uri) -> Unit,
    onStopPlay: () -> Unit,
    modifier: Modifier = Modifier,
    isPlaying: Boolean
) {

    val spacing = LocalSpacing.current

    var selectedIndex by remember { mutableIntStateOf(-1) }
    var currentPlayingIndex by remember { mutableIntStateOf(-1) }
    Scaffold { innerPadding ->

        LazyColumn(
                modifier = modifier.padding(innerPadding),
                contentPadding = PaddingValues(horizontal = spacing.spaceMedium)
        ) {

            item {

                RingtoneRow(
                        modifier = Modifier.padding(spacing.spaceSmall),
                        ringtoneTitle = "Silent",
                        isSilent = true,
                        isSelected = false,
                        onSelectRingtone = {

                            // TODO: Implement Silent Functionality
                        },
                )
            }
            itemsIndexed(items = ringtones) { i, ringtone ->

                val isSelected = selectedIndex == i
                RingtoneRow(
                        modifier = Modifier.padding(spacing.spaceSmall),
                        ringtoneTitle = ringtone.ringtoneName,
                        isSilent = false,
                        isSelected = isSelected,
                        onSelectRingtone = {
                            selectedIndex = i

                            if (currentPlayingIndex == i) {
                                if (isPlaying) {
                                    onStopPlay()
                                    currentPlayingIndex = -1
                                } else {
                                    onPlayRingtone(ringtone.ringtoneUri)
                                    currentPlayingIndex = i
                                }
                            } else {
                                if (isPlaying) {
                                    onStopPlay()
                                }
                                onPlayRingtone(ringtone.ringtoneUri)
                                currentPlayingIndex = i
                            }

                        },
                )
            }

        }

    }
}

@Composable
fun RingtoneRow(
    ringtoneTitle: String,
    isSilent: Boolean,
    onSelectRingtone: () -> Unit,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {

    val spacing = LocalSpacing.current

    Row(
            modifier = modifier
                    .clip(RoundedCornerShape(spacing.spaceSmall))
                    .background(MaterialTheme.colorScheme.surface)
                    .clickable {
                        onSelectRingtone()
                    }
                    .fillMaxWidth()
                    .padding(
                            vertical = spacing.spaceMedium,
                            horizontal = spacing.spaceMedium
                    ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                    modifier = Modifier
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.background)
                            .padding(spacing.spaceSmall), contentAlignment = Alignment.Center
            ) {

                Icon(
                        modifier = Modifier
                                .padding(spacing.spaceSmall),
                        painter = if (isSilent)
                            painterResource(R.drawable.notification_silent)
                        else
                            painterResource(R.drawable.notification_ringing),
                        contentDescription = ""
                )
            }

            Spacer(modifier = Modifier.width(spacing.spaceSmall))

            Text(
                    text = ringtoneTitle,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.W600
                    , overflow = TextOverflow.Clip,
                    maxLines = 1
            )
        }

        CircularCheckbox(
                modifier = Modifier.clip(CircleShape),
                checked = isSelected
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
                        isSilent = true,
                        isSelected = true,
                        onSelectRingtone = {},
                )
                RingtoneRow(
                        ringtoneTitle = "Blue Hills",
                        isSilent = false,
                        isSelected = false,
                        onSelectRingtone = {},
                )
            }
        }


    }

}

@PreviewLightDark
@Composable
private fun RingtoneContentPreview() {

    SnoozelooTheme {

        RingtoneScreenContent(
                ringtones = getRandomRingtones(),
                onPlayRingtone = {},
                isPlaying = false,
                onStopPlay = {}
        )
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
                            ringtoneName = "$ringtoneTitle ${it + 1}",
                            ringtoneUri = Uri.parse(ringtoneTitle)
                    )
            )

        }
    }

}