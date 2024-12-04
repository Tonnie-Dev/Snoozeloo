package com.tonyxlab.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.presentation.ui.theme.LocalSpacing
import com.tonyxlab.presentation.ui.theme.SnoozelooTheme

@Composable
fun CircularCheckbox(
    checked: Boolean,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current
    Box(
            modifier = modifier
                    .clip(CircleShape)
                    .background(if (checked) MaterialTheme.colorScheme.primary else Color.Transparent)
                    .size(spacing.spaceLarge)
                    .padding(spacing.spaceExtraSmall),
            contentAlignment = Alignment.Center
    ) {
        if (checked) {
            Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                            .align(Alignment.Center)
                            .size(spacing.spaceMedium)
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun CircularCheckboxPreview() {

    val spacing = LocalSpacing.current
    SnoozelooTheme {

        Surface {

            Column {

                CircularCheckbox(
                        checked = true,
                )
                Spacer(modifier = Modifier.height(spacing.spaceMedium))

                CircularCheckbox(
                        checked = false,
                )
            }
        }
    }
}