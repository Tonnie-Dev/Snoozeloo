package com.tonyxlab.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.tonyxlab.presentation.ui.theme.LocalSpacing
import com.tonyxlab.presentation.ui.theme.SnoozelooTheme
import com.tonyxlab.utils.getPrimaryColor


@Composable
fun NumberInputField(
    value: String,
    isError: Boolean,
    onValueChanged: ((String) -> Unit)?,
    modifier: Modifier = Modifier,
    maxLines: Int = 1,
    hint: String = "00"
) {
    val spacing = LocalSpacing.current
    val focusManager = LocalFocusManager.current
    var textState by remember { mutableStateOf(TextFieldValue(value)) }


    var isFocused by remember { mutableStateOf(false) }
    val textStyle = MaterialTheme.typography.displayLarge
    val shape = RoundedCornerShape(spacing.spaceDoubleDp * 5)
    val focusedBorderModifier = if (isFocused) {
        modifier.border(
                width = spacing.spaceDoubleDp,
                color = MaterialTheme.colorScheme.primary,
                shape = shape
        )
    } else {
        modifier
    }


    /*
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

    */
    BasicTextField(

            modifier = modifier
                    .onFocusChanged {

                        isFocused = it.isFocused
                    }
                    .then(focusedBorderModifier),
            value = value,
            onValueChange = { onValueChanged?.invoke(it) },
            decorationBox = {
                Box(
                        modifier = Modifier
                                .clip(shape = shape)
                                .size(128.dp, 95.dp)
                                // .height(spacing.spaceOneHundred)

                                .background(color = MaterialTheme.colorScheme.background),
                        contentAlignment = Alignment.Center
                ) {


                    if (value.isBlank() && !isFocused) {

                        Text(
                                text = hint,
                                style = textStyle,
                                color = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                        )
                    } else if (value.isNotBlank()) {
                        Box(
                                modifier = Modifier.fillMaxWidth(.5f),
                                contentAlignment = Alignment.Center
                        ) {
                            it()
                        }
                    }
                }
            },
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
                    TransformedText(
                            text = AnnotatedString.Builder()
                                    .apply {
                                        withStyle(style = SpanStyle(color = getPrimaryColor())) {
                                            append(annotatedString.text)
                                        }
                                    }
                                    .toAnnotatedString(),
                            offsetMapping = OffsetMapping.Identity
                    )
                }

            },

            cursorBrush = SolidColor(Color.Transparent),
            maxLines = maxLines,
            singleLine = true,
            textStyle = textStyle,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            keyboardActions = KeyboardActions(
                    onDone = {

                        focusManager.clearFocus(force = true)
                    }
            )
    )

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
/*
Modifier
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

},*/

