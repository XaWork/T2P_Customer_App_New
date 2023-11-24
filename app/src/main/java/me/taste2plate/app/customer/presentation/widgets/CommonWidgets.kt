package me.taste2plate.app.customer.presentation.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.taste2plate.app.customer.presentation.theme.ForestGreen
import me.taste2plate.app.customer.presentation.theme.ForestGreenDark
import me.taste2plate.app.customer.presentation.theme.LowPadding
import me.taste2plate.app.customer.presentation.theme.LowRoundedCorners
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.backgroundColor
import me.taste2plate.app.customer.presentation.theme.primaryColor
import me.taste2plate.app.customer.presentation.utils.rupeeSign

@Composable
fun InfoWithIcon(
    modifier: Modifier = Modifier,
    icon: Boolean = false,
    imageVector: ImageVector = Icons.Default.LocationOn,
    id: Int = 0,
    info: String? = null,
    infoAnnotated: AnnotatedString = buildAnnotatedString {
        withStyle(SpanStyle()) {
            append("")
        }
    },
    iconOrImageModifier: Modifier = Modifier,
    colorFilter: ColorFilter? = null,
    tint: Color = LocalContentColor.current,
    maxLines: Int = 1,
    textColor: Color = Color.Unspecified,
    fontSize: TextUnit = 12.sp,
    iconInStart: Boolean = true
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (iconInStart)
            if (icon)
                MaterialIcon(imageVector = imageVector, tint = tint, modifier = iconOrImageModifier)
            else
                DrawableImage(id = id, colorFilter = colorFilter, modifier = iconOrImageModifier)

        HorizontalSpace(space = SpaceBetweenViewsAndSubViews)

        if (infoAnnotated.text.isNotEmpty())
            Text(
                text = infoAnnotated,
                fontSize = fontSize,
                overflow = TextOverflow.Ellipsis,
                maxLines = maxLines,
                color = textColor
            )
        else
            Text(
                text = info!!,
                fontSize = fontSize,
                overflow = TextOverflow.Ellipsis,
                maxLines = maxLines,
                color = textColor
            )


        if (!iconInStart) {
            HorizontalSpace(space = SpaceBetweenViewsAndSubViews)
            if (icon)
                MaterialIcon(imageVector = imageVector, tint = tint, modifier = iconOrImageModifier)
            else
                DrawableImage(id = id, colorFilter = colorFilter, modifier = iconOrImageModifier)
        }


    }
}

@Composable
fun RatingInfoRow(
    modifier: Modifier = Modifier,
    flatOff: String,
    rating: String,
    weight: String,
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

        Text(
            "$weight Kg",
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
    item1: @Composable RowScope.() -> Unit,
    item2: @Composable RowScope.() -> Unit,
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
fun SpaceBetweenRow(
    modifier: Modifier = Modifier,
    items: List<@Composable RowScope.() -> Unit>
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        for (item in items)
            item()
    }
}


@Composable
fun VegNonVegFilter(
    modifier: Modifier = Modifier,
    switchModifier: Modifier = Modifier,
    checked: Boolean = false,
    fontSize: TextUnit = 12.sp,
    onCheckChange: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(horizontal = LowPadding)
    ) {
        Text(text = "Veg", fontSize = fontSize)

        Switch(
            checked = checked, onCheckedChange = { onCheckChange() },
            modifier = switchModifier
                .scale(0.6f),
            colors = SwitchDefaults.colors(
                checkedBorderColor = backgroundColor.invoke(),
                checkedTrackColor = backgroundColor.invoke(),
                checkedThumbColor = Color.Red,
                uncheckedThumbColor = ForestGreen,
                uncheckedBorderColor = backgroundColor.invoke(),
                uncheckedTrackColor = backgroundColor.invoke()
            )
        )

        Text(text = "Non-veg", fontSize = fontSize)
    }
}

@Preview
@Composable
fun SwitchPreview() {
    Switch(
        checked = true,
        onCheckedChange = {},
        modifier = Modifier.scale(1f)
    )
}