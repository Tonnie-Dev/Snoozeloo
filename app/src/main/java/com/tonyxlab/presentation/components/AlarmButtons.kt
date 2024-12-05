package com.tonyxlab.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
            modifier = modifier
                    .clip(RoundedCornerShape(spacing.spaceSmall))
                    .background(
                            color = if (isEnabled) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurface.copy(alpha = .12f)
                    ).clickable { onClickIcon() }
                    ,
            contentAlignment = Alignment.Center
    ) {



            Icon(modifier = Modifier.padding(spacing.spaceSmall),
                    imageVector = icon,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = contentDescription
            )


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
            modifier = modifier.padding(
                    horizontal = spacing.spaceMedium,
                    vertical = spacing.spaceSmall
            ),
            onClick = onClick,
            enabled = isEnabled,
            colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContentColor =Color.White
            )
    ) {
        Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.W600
        )
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


    Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
    ) {

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
