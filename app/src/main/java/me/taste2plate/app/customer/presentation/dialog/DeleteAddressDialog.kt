package me.taste2plate.app.customer.presentation.dialog

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.taste2plate.app.customer.presentation.theme.primaryColor
import me.taste2plate.app.customer.presentation.theme.screenBackgroundColor


@Composable
fun DeleteAddressDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    AlertDialog(
        containerColor = screenBackgroundColor.invoke(),
        icon = {
            Icon(
                Icons.Default.Delete,
                contentDescription = "Example Icon",
                tint = primaryColor.invoke()
            )
        },
        title = {
            Text(text = "Delete Address")
        },
        text = {
            Text(text = "Do you really want to delete this address?")
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}

@Preview
@Composable
fun DelteAddressDialogPreview() {
    DeleteAddressDialog(
        onConfirmation = {},
        onDismissRequest = {}
    )
}