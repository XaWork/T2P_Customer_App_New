package me.taste2plate.app.customer.presentation.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun AppCheckBox(
    checked: Boolean = false,
    onCheckedChange: () -> Unit,
    text: String
) {
    Row {
        Checkbox(checked = checked, onCheckedChange = { onCheckedChange() })
        Text(text = text)
    }
}