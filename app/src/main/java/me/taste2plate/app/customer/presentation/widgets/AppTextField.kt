package me.taste2plate.app.customer.presentation.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit,
    hint: String,
    leadingIcon: @Composable () -> Unit = {}
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        value = "",
        onValueChange = { onValueChanged(it) },
        leadingIcon = leadingIcon,
        label = { Text(text = hint) },
    )
}