package com.tonyxlab.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.R
import com.tonyxlab.presentation.ui.theme.LocalSpacing
import com.tonyxlab.presentation.ui.theme.SnoozelooTheme

@Composable
fun EmptyScreen(modifier: Modifier = Modifier) {

    val spacing = LocalSpacing.current

    Column(
            modifier = Modifier
                    .fillMaxSize()
                    .padding(spacing.spaceMedium),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
                modifier = Modifier.size(spacing.spaceExtraLarge),
                painter = painterResource(R.drawable.alarm),
                contentDescription = ""
        )

        Spacer(modifier = Modifier.height(spacing.spaceLarge))

        Text(
                text = stringResource(R.string.empty_screen_text),
                textAlign = TextAlign.Center
        )

    }


}


@PreviewLightDark
@Composable
private fun EmptyScreenPreview() {
    SnoozelooTheme {

        Surface {

            EmptyScreen()
        }
    }
}