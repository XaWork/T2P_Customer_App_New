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
fun PriceDialog(
    minOrder: String,
    walletPoint: String,
    onConfirmation: () -> Unit,
) {
    AlertDialog(
        containerColor = screenBackgroundColor.invoke(),
        title = {
            Text(text = "Alert!")
        },
        text = {
            Text(
                text = "Minimum order value is ₹${minOrder} to redeem wallet points\n\n" +
                        "Maximum $walletPoint points to be redeemed in each order"
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