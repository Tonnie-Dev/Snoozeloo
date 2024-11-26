package com.tonyxlab.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.sp
import com.tonyxlab.R
import com.tonyxlab.presentation.ui.theme.SnoozelooTheme

@Composable
fun ModalDialog(
    title: String,
    isShowDialog: Boolean,
    modifier: Modifier = Modifier,
    onConfirmDialog: () -> Unit,
    onDismissDialog: () -> Unit,
) {
    var textValue by remember { mutableStateOf("") }
    var isSaveEnabled by remember { mutableStateOf(false) }

    if (isShowDialog) {

        AlertDialog(
                modifier = modifier,
                onDismissRequest = onDismissDialog,
                title = { Text(text = title) },
                text = {
                    OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = textValue,
                            onValueChange = {
                                textValue = it
                                if (it.isNotEmpty()) {
                                    isSaveEnabled = true
                                }
                            },
                            singleLine = true,
                            textStyle = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.W500)
                    )
                },
                confirmButton = {
                    Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.End
                    ) {

                        MediumButton(
                                text = stringResource(id = R.string.save_text),
                                onClick = { onConfirmDialog() },
                                isEnabled = isSaveEnabled
                        )
                    }
                },
        )

    }

}

@PreviewLightDark
@Composable
private fun ModalDialogPreview() {
    SnoozelooTheme {

        Surface {

            ModalDialog(
                    title = stringResource(id = R.string.alarm_name_text),
                    isShowDialog = true,
                    onConfirmDialog = {},
                    onDismissDialog = {},
            )
        }
    }
}