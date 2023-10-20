package me.taste2plate.app.customer.presentation.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    text: String = "",
    paddingValues: PaddingValues = PaddingValues(horizontal = 10.dp),
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth(),
        onClick = { onClick() }) {
        Text(text = text.uppercase())
    }
}

@Preview
@Composable
fun ButtonPreview() {
    AppButton(text = "Button") {

    }
}