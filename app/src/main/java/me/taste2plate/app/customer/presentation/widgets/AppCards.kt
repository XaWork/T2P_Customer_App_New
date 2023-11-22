package me.taste2plate.app.customer.presentation.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.taste2plate.app.customer.presentation.theme.MediumRoundedCorners
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.backgroundColor
import me.taste2plate.app.customer.presentation.theme.cardContainerOnSecondaryColor

@Composable
fun RedBorderCard(
    modifier: Modifier = Modifier,
    cardColor: CardColors = CardDefaults.cardColors(),
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = ScreenPadding,
                end = ScreenPadding,
                bottom = ScreenPadding
            ),
        colors = CardDefaults.cardColors(containerColor = backgroundColor.invoke()),
        shape = RoundedCornerShape(MediumRoundedCorners),
        border = BorderStroke(
            width = 0.5.dp,
            color = MaterialTheme.colorScheme.primary
        )
    ) {
        content()
    }
}

@Composable
fun BlackBorderCard(
    modifier: Modifier = Modifier,
    colors: CardColors = CardDefaults.cardColors(),
    borderWidth: Dp = 0.5.dp,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = ScreenPadding,
                end = ScreenPadding,
                bottom = ScreenPadding
            ),
        shape = RoundedCornerShape(MediumRoundedCorners),
        border = BorderStroke(
            width = borderWidth,
            color = MaterialTheme.colorScheme.onSurface
        ),
        colors = colors
    ) {
        content()
    }
}

@Composable
fun RoundedCornerCard(
    modifier: Modifier = Modifier,
    cardCornerRadius: Dp = MediumRoundedCorners,
    elevation: Dp = 0.dp,
    cardColor: CardColors = CardDefaults.cardColors(),
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(cardCornerRadius),
        elevation = CardDefaults.cardElevation(elevation),
        colors = cardColor
    ) {
        content()
    }
}