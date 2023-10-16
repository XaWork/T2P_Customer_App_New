package me.taste2plate.app.customer.presentation.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews

@Composable
fun InfoWithIcon(
    icon: Boolean = false,
    imageVector: ImageVector = Icons.Default.LocationOn,
    id: Int = 0,
    info: String,
    colorFilter: ColorFilter? = null,
    tint: Color = LocalContentColor.current,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon)
            MaterialIcon(imageVector = imageVector)
        else
            DrawableImage(id = id, colorFilter = colorFilter)
        HorizontalSpace(space = SpaceBetweenViewsAndSubViews)
        Text(text = info)
    }
}