package com.tonyxlab.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    textValue: String,
    isDialogSaveButtonEnabled: Boolean,
    onConfirmDialog: () -> Unit,
    onDismissDialog: () -> Unit,
    onValueChange: ((String) -> Unit)?,
    modifier: Modifier = Modifier,
) {

    if (isShowDialog) {

        AlertDialog(
                modifier = modifier,
                onDismissRequest = onDismissDialog,
                title = { Text(text = title) },
                text = {
                    OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = textValue,
                            onValueChange = { onValueChange?.invoke(it) },
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
                                isEnabled = isDialogSaveButtonEnabled
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
                    textValue = "Name",
                    isDialogSaveButtonEnabled = true,
                    onConfirmDialog = {},
                    onDismissDialog = {},
                    onValueChange = {}
            )
        }
    }
}