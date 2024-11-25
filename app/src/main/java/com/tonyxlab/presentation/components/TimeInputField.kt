package com.tonyxlab.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation.Companion.keyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tonyxlab.presentation.ui.theme.LocalSpacing
import com.tonyxlab.presentation.ui.theme.SnoozelooTheme

@Composable
fun TimeInputField(
    initialText: String,
    range: IntRange,
    modifier: Modifier = Modifier
) {

    val spacing = LocalSpacing.current
    var textValue by remember { mutableStateOf(initialText) }
    var isError by remember { mutableStateOf(false) }

    Row (modifier = modifier.size(spacing.spaceOneHundredFifty)){


        TextField(
                modifier = Modifier

                        .padding(
                                horizontal = spacing.spaceLarge,
                                vertical = spacing.spaceMedium
                        ),
                value = textValue,
                onValueChange = {
                    if (it.length <= 2) {
                        isError = it.toIntOrNull()
                                ?.let { num -> num !in range } ?: true
                        textValue = it
                    }
                },
                textStyle = TextStyle(fontWeight = FontWeight.W500, fontSize = 57.sp),
                isError = isError,
                singleLine = true,

                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }}





@PreviewLightDark
@Composable
private fun TimeInputFieldPreview() {

    val spacing = LocalSpacing.current
    SnoozelooTheme {
        Surface {

            TimeInputField(
                    modifier = Modifier.wrapContentSize(),
                    initialText = "13",
                    range = 0..10
            )
        }
    }
}


