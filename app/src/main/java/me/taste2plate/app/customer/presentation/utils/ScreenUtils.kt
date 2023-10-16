package me.taste2plate.app.customer.presentation.utils

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val configuration: @Composable () -> Configuration = {
    LocalConfiguration.current
}

val screenWidth: @Composable () -> Dp = {
    configuration.invoke().screenWidthDp.dp
}

val screenHeight: @Composable () -> Dp = {
    configuration.invoke().screenHeightDp.dp
}