package com.tonyxlab.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.tonyxlab.presentation.ui.theme.LocalSpacing
import com.tonyxlab.presentation.ui.theme.SnoozelooTheme

@Composable
fun TimeInputField(range: IntRange, modifier: Modifier = Modifier) {

    val spacing = LocalSpacing.current
    var textValue by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    TextField(
            modifier = Modifier.padding(
                    horizontal = spacing.spaceLarge,
                    vertical = spacing.spaceMedium
            ),
            value = textValue,
            onValueChange = {
                if (it.length <= 2){

                    isError = it.toIntOrNull()
                            ?.let { num -> num !in range } ?: true
                    textValue = it
                }


            },
            isError = isError,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}


@PreviewLightDark
@Composable
private fun TimeInputFieldPreview() {
    SnoozelooTheme {

        Surface {

            TimeInputField(range = 0..10)
        }
    }
}


/*fun rangeCheck(number: Int, range: IntRange): String {

    if (number in range) number.toString()

}*/
