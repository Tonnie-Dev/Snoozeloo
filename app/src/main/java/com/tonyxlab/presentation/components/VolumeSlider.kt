package com.tonyxlab.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderDefaults.Track
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.tonyxlab.presentation.ui.theme.LocalSpacing
import com.tonyxlab.presentation.ui.theme.SnoozelooTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VolumeSlider(
    value: Float,
    onValueChange: ((Float) -> Unit)?,
    modifier: Modifier = Modifier
) {
    Slider(
            value = value,
            onValueChange = {onValueChange?.invoke(it)},
            modifier = modifier,
            thumb = {
                RoundSliderThumb()
            },
            track = {

                Track(
                        modifier = Modifier.height(8.dp),
                        sliderState = it,
                        colors = SliderDefaults.colors(),
                        thumbTrackGapSize = 0.dp,
                        trackInsideCornerSize = 0.dp,
                        drawStopIndicator = null
                )
            }
    )
}

@Composable
fun RoundSliderThumb() {

    val spacing = LocalSpacing.current
    val primaryColor = MaterialTheme.colorScheme.primary
    Canvas(
            modifier = Modifier
                    .padding(4.dp)
                    .size(spacing.spaceLarge)
    ) {

        drawCircle(
                color = primaryColor,
                center = Offset(size.width / 2, size.height / 2),
                radius = size.width / 2 - 4.dp.toPx(),
        )
    }
}


@PreviewLightDark
@Composable
private fun SliderPreview() {
    SnoozelooTheme {
        Surface {
            VolumeSlider(
                    value = .5f,
                    onValueChange = {}
            )
        }
    }

}

