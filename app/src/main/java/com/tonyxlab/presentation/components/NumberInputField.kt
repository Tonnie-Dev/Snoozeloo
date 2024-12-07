package com.tonyxlab.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.tonyxlab.presentation.ui.theme.LocalSpacing
import com.tonyxlab.presentation.ui.theme.SnoozelooTheme


@Composable
fun NumberInputField(
    value: String,
    isError: Boolean,
    onValueChanged: ((String) -> Unit)?,
    modifier: Modifier = Modifier
) {


    val spacing = LocalSpacing.current
    Box(
            modifier = modifier
                    .size(128.dp, 95.dp)
                    .padding(
                            top = spacing.spaceMedium,
                            bottom = spacing.spaceMedium,
                            start = 24.dp
                    ),
            contentAlignment = Alignment.Center
    ) {


        BasicTextField(

                value = value,
                onValueChange = { onValueChanged?.invoke(it) },
                visualTransformation = { annotatedString ->
                    if (isError) {
                        TransformedText(
                                text = AnnotatedString.Builder()
                                        .apply {
                                            withStyle(style = SpanStyle(color = Color.Red)) {
                                                append(annotatedString.text)
                                            }
                                        }
                                        .toAnnotatedString(),
                                offsetMapping = OffsetMapping.Identity
                        )
                    } else {
                        TransformedText(annotatedString, OffsetMapping.Identity)
                    }

                },

                singleLine = true,
                textStyle = MaterialTheme.typography.displayLarge,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

    }

}


@PreviewLightDark
@Composable
private fun NumberInputFieldPreview() {
    val spacing = LocalSpacing.current
    SnoozelooTheme {
        Surface {

            Column {
                NumberInputField(
                        value = "13",
                        onValueChanged = {},
                        isError = false,

                        )

                Spacer(modifier = Modifier.height(spacing.spaceMedium))
                NumberInputField(
                        value = "13",
                        onValueChanged = {},
                        isError = true,

                        )
            }
        }
    }
}

