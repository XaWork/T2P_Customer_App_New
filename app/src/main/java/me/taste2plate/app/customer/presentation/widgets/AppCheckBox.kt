package me.taste2plate.app.customer.presentation.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.TextUnit

@Composable
fun AppCheckBox(
    checked: Boolean = false,
    onCheckedChange: () -> Unit,
    text: String,
    fontSize: TextUnit = TextUnit.Unspecified
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = checked, onCheckedChange = { onCheckedChange() })
        Text(text = text, fontSize = fontSize )
    }
}