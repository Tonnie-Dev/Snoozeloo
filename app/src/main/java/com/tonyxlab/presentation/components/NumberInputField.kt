package com.tonyxlab.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
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
    val focusManager = LocalFocusManager.current
    var textState by remember { mutableStateOf(TextFieldValue(value)) }

    Box(
            modifier = modifier
                    .size(128.dp, 95.dp)
                    .clickable {
                        textState = textState.copy(
                                selection = androidx.compose.ui.text.TextRange(
                                        0,
                                        textState.text.length
                                )
                        )

                    }
                    .padding(
                            top = spacing.spaceMedium,
                            bottom = spacing.spaceMedium,
                            start = 24.dp
                    ),
            contentAlignment = Alignment.Center
    ) {

        BasicTextField(

                modifier = Modifier
                        .pointerInput(Unit) {
                            detectTapGestures(
                                    onPress = {
                                        textState = textState.copy(
                                                selection = androidx.compose.ui.text.TextRange(
                                                        0,
                                                        textState.text.length
                                                )
                                        )

                                    },
                                    onTap = {
                                        textState = textState.copy(
                                                selection = androidx.compose.ui.text.TextRange(
                                                        0,
                                                        textState.text.length
                                                )
                                        )

                                    }
                            )
                        }
                        .clickable {
                            textState = textState.copy(
                                    selection = androidx.compose.ui.text.TextRange(
                                            0,
                                            textState.text.length
                                    )
                            )

                        },
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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                keyboardActions = KeyboardActions(
                        onDone = {

                            focusManager.clearFocus(force = true)
                        }
                )
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

