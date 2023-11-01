package me.taste2plate.app.customer.presentation.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ShowLoading() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        CircularProgressIndicator()
    }
}