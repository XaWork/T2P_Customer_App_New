package me.taste2plate.app.customer.presentation.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ShowLoading(
    isButton: Boolean = true,
    progressBarModifier: Modifier = Modifier,
    boxModifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = if (isButton) boxModifier.fillMaxWidth() else boxModifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = progressBarModifier.size(20.dp),
            strokeWidth = 3.dp)
    }
}

@Preview
@Composable
fun LoadingPreview() {
    ShowLoading()
}