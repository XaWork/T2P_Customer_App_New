package me.taste2plate.app.customer.presentation.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@Composable
fun TextAlignEnd(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified

) {
    Text(
        text = text,
        modifier = modifier.fillMaxWidth(),
        textAlign = TextAlign.End,
        color = color
    )
}
@Composable
fun TextAlignCenter(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified

) {
    Text(
        text = text,
        modifier = modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        color = color
    )
}