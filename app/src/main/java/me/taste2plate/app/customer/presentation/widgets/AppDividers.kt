package me.taste2plate.app.customer.presentation.widgets

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.dividerThickness

@Composable
fun AppDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = dividerThickness
) {
    Divider(
        modifier = modifier
            .padding(vertical = SpaceBetweenViewsAndSubViews),
        thickness = thickness
    )
}