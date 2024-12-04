package com.tonyxlab.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.tonyxlab.R

@@Composable
fun AppTopBar(
    isCloseButtonEnabled: Boolean,
    onClose: () -> Unit,
    smallButtonIcon: ImageVector,
    smallButton: @Composable () -> Unit,
    mediumButton: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {

    Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
    ) {


        SmallButton(
                icon = smallButtonIcon,
                onClickIcon = onClose,
                isEnabled = isCloseButtonEnabled,
                contentDescription = stringResource(R.string.close_window_text),
        )

        mediumButton?.invoke()
    }
}
