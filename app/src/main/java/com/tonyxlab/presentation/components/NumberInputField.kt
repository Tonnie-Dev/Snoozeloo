package com.tonyxlab.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.tonyxlab.presentation.ui.theme.LocalSpacing
import com.tonyxlab.presentation.ui.theme.SnoozelooTheme
import com.tonyxlab.utils.getHourString
import com.tonyxlab.utils.now
import kotlinx.datetime.LocalDateTime


@Composable
fun NumberInputField(
    initialText: String,
    range: IntRange,
    allowedCharacters: Int = 2,
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf(initialText) }
    var isError by remember { mutableStateOf(false) }
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

                value = text,
                onValueChange = { newInput ->

                    if (newInput.length <= allowedCharacters) {

                        text = newInput
                        isError = newInput.toIntOrNull()
                                ?.let { int -> int !in range } ?: true
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


            NumberInputField(

                    initialText = LocalDateTime.now()
                            .getHourString(),
                    range = 0..23
            )
        }
    }
}

