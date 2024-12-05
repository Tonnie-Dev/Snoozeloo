package com.tonyxlab.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.R
import com.tonyxlab.presentation.ui.theme.LocalSpacing
import com.tonyxlab.presentation.ui.theme.SnoozelooTheme

@Composable
fun AppTopBar(
    isSmallButtonEnabled: Boolean,
    onClickSmallButton: () -> Unit,
    smallButtonIcon: ImageVector,
    mediumButton: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
val spacing = LocalSpacing.current
    Row(
            modifier = modifier.fillMaxWidth().padding(spacing.spaceMedium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
    ) {


        SmallButton(
                icon = smallButtonIcon,
                onClickIcon = onClickSmallButton,
                isEnabled = isSmallButtonEnabled,
                contentDescription = stringResource(R.string.close_window_text),
        )

        mediumButton?.invoke()
    }
}


@PreviewLightDark
@Composable
private fun AppTopPreview() {

    val spacing = LocalSpacing.current
    SnoozelooTheme {


        Surface() {

            Column {


                AppTopBar(
                        isSmallButtonEnabled = true,
                        onClickSmallButton = {},
                        smallButtonIcon = Icons.AutoMirrored.Filled.ArrowBack,
                        mediumButton = {
                            MediumButton(
                                    modifier = Modifier.wrapContentSize(),
                                    text = "Save",
                                    isEnabled = true,
                                    onClick = {}
                            )
                        }
                )


                Spacer(modifier = Modifier.height(spacing.spaceSmall))

                AppTopBar(
                        isSmallButtonEnabled = true,
                        onClickSmallButton = {},
                        smallButtonIcon = Icons.AutoMirrored.Filled.ArrowBack,
                        mediumButton = {
                            MediumButton(
                                    text = "Save",
                                    isEnabled = true,
                                    onClick = {}
                            )
                        }
                )
            }
        }
    }
}
