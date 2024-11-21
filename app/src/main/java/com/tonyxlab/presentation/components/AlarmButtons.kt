package com.tonyxlab.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.R
import com.tonyxlab.presentation.ui.theme.LocalSpacing
import com.tonyxlab.presentation.ui.theme.SnoozelooTheme

@Composable
fun SmallButton(
    icon: ImageVector,
    onClickIcon: () -> Unit,
    isEnabled: Boolean,
    contentDescription: String = stringResource(id = R.string.icon_text),
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current
    Box(
            modifier = modifier.clip(RoundedCornerShape(spacing.spaceMedium)),
            contentAlignment = Alignment.Center
    ) {

        IconButton(onClick = onClickIcon) {

            Icon(
                    imageVector = icon,
                    contentDescription = contentDescription
            )
        }

    }
}

@Composable
fun MediumButton(
    text: String,
    onClick: () -> Unit,
    isEnabled: Boolean,
    modifier: Modifier = Modifier
) {


    val spacing = LocalSpacing.current

    Button(
            modifier = Modifier.padding(
                    horizontal = spacing.spaceMedium,
                    vertical = spacing.spaceSmall
            ),
            onClick = onClick,
            enabled = isEnabled
    ) {
        Text(text = text)
    }


}

@PreviewLightDark
@Composable
private fun SmallButtonPreview() {
    val spacing = LocalSpacing.current
    SnoozelooTheme {
        Surface {
            Column {

                SmallButton(
                        icon = Icons.AutoMirrored.Filled.ArrowBack,
                        onClickIcon = {},
                        isEnabled = true,
                        contentDescription = ""
                )
                Spacer(modifier = Modifier.height(spacing.spaceMedium))
                SmallButton(
                        icon = Icons.Default.Close,
                        onClickIcon = {},
                        isEnabled = false,
                        contentDescription = ""
                )

            }
        }
    }
}


@PreviewLightDark
@Composable
private fun MediumButtonPreview() {

    SnoozelooTheme {

        Surface {

            Column {

                MediumButton(
                        text = stringResource(R.string.save_text),
                        onClick = {},
                        isEnabled = true
                )


                MediumButton(
                        text = stringResource(R.string.save_text),
                        onClick = {},
                        isEnabled = false
                )
            }
        }
    }
}