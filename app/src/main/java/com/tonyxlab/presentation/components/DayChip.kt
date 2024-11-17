package com.tonyxlab.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.presentation.ui.theme.LocalSpacing
import com.tonyxlab.presentation.ui.theme.SnoozelooTheme
import com.tonyxlab.utils.getBlue_100
import com.tonyxlab.utils.getBlue_600


@Composable
fun DayChip(
    text: String,
    onClick: () -> Unit,
    isSelected: Boolean,
    activeColor: Color = getBlue_600(),
    inactiveColor: Color = getBlue_100(),
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current

    Box(
            modifier = modifier
                    .clip(RoundedCornerShape(spacing.spaceMedium))
                    .background(color = if (isSelected) activeColor else inactiveColor)
                    .clickable { onClick() }
                    .padding(spacing.spaceExtraSmall),
            contentAlignment = Alignment.Center
    ) {

        Text(text = text)
    }
}


@PreviewLightDark
@Composable
private fun DayChipPreview() {

    val spacing = LocalSpacing.current
    SnoozelooTheme {

        Surface {
            Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(spacing.spaceExtraLarge)
            ) {
                DayChip(
                        text = "Tu",
                        onClick = {},
                        isSelected = true,
                )

                DayChip(
                        text = "Tu",
                        onClick = {},
                        isSelected = false,
                )
            }

        }
    }
}
