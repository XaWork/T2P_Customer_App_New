package me.taste2plate.app.customer.presentation.dialog

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import me.taste2plate.app.customer.presentation.theme.primaryColor
import me.taste2plate.app.customer.presentation.theme.screenBackgroundColor

@Composable
fun CustomDialog(
    title: String,
    text: String,
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
            Text(text = title)
        },
        text = {
            Text(
                text = text
            )
        },
        onDismissRequest = {
            onConfirmation()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Ok")
            }
        }
    )
}