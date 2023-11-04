package me.taste2plate.app.customer.presentation.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

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
        CircularProgressIndicator(modifier = progressBarModifier)
    }
}