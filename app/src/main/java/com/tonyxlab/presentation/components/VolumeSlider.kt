package com.tonyxlab.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.tonyxlab.presentation.ui.theme.SnoozelooTheme

@Composable
fun RoundSliderThumb() {
    Canvas(
            modifier = Modifier.padding(4.dp)
                    .size(48.dp)
    ) {
        drawCircle(
                color = Color.White,
                center = Offset(size.width / 2, size.height / 2),
                radius = size.width / 2,
                style = Stroke(width = 2f, cap = StrokeCap.Round)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSlider() {
    Slider(
            value = 0.5f,
            onValueChange = { value: Float -> /* handle value change */ },
            thumb = { RoundSliderThumb() },
            track = {
           
            }



    )
}


@PreviewLightDark
@Composable
private fun SliderPreview() {

    SnoozelooTheme {

        Surface {

            CustomSlider()
        }
    }
}