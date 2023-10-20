package me.taste2plate.app.customer.presentation.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.taste2plate.app.customer.presentation.theme.ForestGreen
import me.taste2plate.app.customer.presentation.theme.ForestGreenDark
import me.taste2plate.app.customer.presentation.theme.LowPadding
import me.taste2plate.app.customer.presentation.theme.LowRoundedCorners
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.utils.rupeeSign

@Composable
fun InfoWithIcon(
    modifier: Modifier = Modifier,
    icon: Boolean = false,
    imageVector: ImageVector = Icons.Default.LocationOn,
    id: Int = 0,
    info: String,
    iconOrImageModifier: Modifier = Modifier,
    colorFilter: ColorFilter? = null,
    tint: Color = LocalContentColor.current,
    maxLines: Int = 1,
    textColor: Color = Color.Unspecified,
    fontSize: TextUnit = 12.sp
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon)
            MaterialIcon(imageVector = imageVector, tint = tint, modifier = iconOrImageModifier)
        else
            DrawableImage(id = id, colorFilter = colorFilter, modifier = iconOrImageModifier)
        HorizontalSpace(space = SpaceBetweenViewsAndSubViews)
        Text(
            text = info,
            fontSize = fontSize,
            overflow = TextOverflow.Ellipsis,
            maxLines = maxLines,
            color = textColor
        )
    }
}

@Composable
fun RatingInfoRow(
    modifier: Modifier = Modifier,
    flatOff: String,
    rating: String,
    showIcon: Boolean = true
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            flatOff,
            color = MaterialTheme.colorScheme.tertiary
        )

        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(LowRoundedCorners))
                .clip(RoundedCornerShape(LowRoundedCorners))
                .background(
                    color = if (isSystemInDarkTheme()) ForestGreenDark
                    else ForestGreen
                )
                .padding(horizontal = LowPadding),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = rating,
                color = MaterialTheme.colorScheme.background,
                fontSize = 14.sp
            )

            if (showIcon)
                MaterialIcon(
                    imageVector = Icons.Rounded.Star,
                    tint = MaterialTheme.colorScheme.background,
                    modifier = Modifier.size(15.dp)
                )
        }
    }
}

@Composable
fun SpaceBetweenRow(
    modifier: Modifier = Modifier,
    item1 : @Composable () -> Unit,
    item2 : @Composable () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        item1()
        item2()
    }
}


@Composable
fun VegNonVegFilter() {
    var checked by remember { mutableStateOf(false) }
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = LowPadding)
    ) {
        Text(text = "Veg")
        Switch(
            checked = checked, onCheckedChange = {
                checked = it
            },
            modifier = Modifier.padding(horizontal = SpaceBetweenViewsAndSubViews)
        )
        Text(text = "Non-veg")
    }
}