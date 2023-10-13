package me.taste2plate.app.customer.presentation.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp

@Composable
fun DrawableIcon(
    modifier: Modifier = Modifier,
    id: Int,
    ) {
    Icon(
        modifier = modifier,
        painter = painterResource(id = id),
        contentDescription = ""
    )
}

@Composable
fun DrawableImage(
    modifier: Modifier = Modifier,
    id: Int,
    ) {
    Image(
        modifier = modifier,
        painter = painterResource(id = id),
        contentDescription = ""
    )
}
@Composable
fun MaterialIcon(
    modifier: Modifier = Modifier,
    imageVector: ImageVector
    ) {
    Icon(
        modifier = modifier,
        imageVector = imageVector,
        contentDescription = ""
    )
}

@Composable
fun VerticalSpace(space: Dp) {
    Spacer(modifier = Modifier.height(space))
}

@Composable
fun HorizontalSpace(space: Dp) {
    Spacer(modifier = Modifier.width(space))
}