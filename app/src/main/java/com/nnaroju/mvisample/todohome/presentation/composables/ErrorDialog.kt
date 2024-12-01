package com.nnaroju.mvisample.todohome.presentation.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.nnaroju.mvisample.R
import com.nnaroju.mvisample.todohome.presentation.TodoHomeScreenEvent

@Composable
fun ShowErrorDialog(
    errorMessage: String,
    onSendEvent: (TodoHomeScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(true) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Text(text = stringResource(R.string.error))
                },
                text = {
                    Text(errorMessage)
                },
                confirmButton = {},
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDialog = false
                            onSendEvent(TodoHomeScreenEvent.FetchAllTodoItems)
                        }
                    ) {
                        Text(stringResource(R.string.dismiss))
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ShowErrorDialogPreview() {
    MaterialTheme {
        ShowErrorDialog(
            errorMessage = "Unknown error",
            onSendEvent = {}
        )
    }
}