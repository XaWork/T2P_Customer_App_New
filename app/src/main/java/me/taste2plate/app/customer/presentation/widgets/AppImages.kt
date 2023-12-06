package me.taste2plate.app.customer.presentation.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.presentation.screens.home.FoodItemUpdateInfo
import me.taste2plate.app.customer.presentation.theme.ExtraLowPadding
import me.taste2plate.app.customer.presentation.theme.LowPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.backgroundColor
import me.taste2plate.app.customer.presentation.theme.primaryColor

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

//Image that store in drawable folder whether it is jpg/png
@Composable
fun DrawableImage(
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    id: Int,
    colorFilter: ColorFilter? = null
) {
    Image(
        modifier = modifier,
        painter = painterResource(id = id),
        contentDescription = "",
        contentScale = contentScale,
        colorFilter = colorFilter
    )
}


//Icons for ex : Icons.Default.Lock
@Composable
fun MaterialIcon(
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
    imageVector: ImageVector,
) {
    Icon(
        modifier = modifier,
        imageVector = imageVector,
        contentDescription = "",
        tint = tint
    )
}

@Composable
fun MaterialIconButton(
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
    imageVector: ImageVector,
    badge: Boolean = false,
    badgeText: Int = 0,
    onclick: () -> Unit
) {
    Box {
        IconButton(onClick = { onclick() }) {
            Icon(
                modifier = modifier,
                imageVector = imageVector,
                contentDescription = "",
                tint = tint
            )
        }

        if (badge)
            Text(
                text = badgeText.toString(),
                modifier = Modifier
                    .background(backgroundColor.invoke())
                    .padding(ExtraLowPadding)
                    .clip(RoundedCornerShape(100.dp))
                    .align(Alignment.TopEnd),
                color = primaryColor.invoke(),
                textAlign = TextAlign.Center,
                fontSize = 10.sp
            )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishlistIconWithCount(
    itemCount: Int, icon: ImageVector,
    onclick: () -> Unit
) {
    BadgedBox(
        modifier = Modifier
            .clickable { onclick() }
            .padding(horizontal = SpaceBetweenViewsAndSubViews),
        badge = {
            if (itemCount != 0)
                Badge { Text(itemCount.toString()) }
        }) {
        Icon(
            imageVector = icon,
            contentDescription = "CALL"
        )
    }
}


@Composable
fun DrawableIconButton(
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
    painterResource: Int,
    onclick: () -> Unit
) {
    IconButton(onClick = { onclick() }) {
        Icon(
            modifier = modifier,
            painter = painterResource(id = painterResource),
            contentDescription = "",
            tint = tint
        )
    }
}

@Composable
fun VerticalSpace(space: Dp) {
    Spacer(modifier = Modifier.height(space))
}

@Composable
fun HorizontalSpace(space: Dp) {
    Spacer(modifier = Modifier.width(space))
}

@Composable
fun CircleIconButton(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    painterResource: Int? = null,
    icon: ImageVector? = null,
    isDrawableIcon: Boolean = true,
    tint: Color = LocalContentColor.current,
    onclick: () -> Unit,
) {
    Card(
        modifier = modifier,
        shape = CircleShape,
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor.invoke()
        )
    ) {
        if (isDrawableIcon)
            DrawableIconButton(
                modifier = iconModifier,
                tint = tint,
                painterResource = painterResource!!,
            ) {
                onclick()
            }
        else
            MaterialIconButton(
                modifier = iconModifier,
                tint = tint,
                imageVector = icon!!
            ) {
                onclick()
            }
    }
}

@Composable
fun RoundedCornerIconButton(
    isMaterialIcon: Boolean = false,
    icon: ImageVector = Icons.Default.LocationOn,
    painterResource: Int = 0,
    cornerRadius: Dp = 10.dp,
    cardColors: CardColors = CardDefaults.cardColors(),
    onclick: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(cornerRadius),
        colors = cardColors
    ) {
        if (isMaterialIcon)
            MaterialIconButton(
                modifier = Modifier.padding(LowPadding),
                imageVector = icon
            ) {
                onclick()
            }
        else
            DrawableIconButton(
                modifier = Modifier.padding(LowPadding),
                painterResource = painterResource,
            ) {
                onclick()
            }
    }
}

@Composable
fun NetworkImage(
    modifier: Modifier = Modifier,
    image: String,
    contentScale: ContentScale = ContentScale.Fit
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(image)
            .build(),
        modifier = modifier,
        placeholder = painterResource(id = R.drawable.placeholder),
        error = painterResource(id = R.drawable.placeholder),
        contentScale = contentScale,
        contentDescription = ""
    )
}

@Composable
fun ImageWithWishlistButton(
    modifier: Modifier = Modifier,
    withButton: Boolean = true,
    alreadyWishListed: Boolean = false,
    image: String,
    contentScale: ContentScale = ContentScale.Crop,
    foodItemUpdateInfo: FoodItemUpdateInfo? = null,
    onclick: () -> Unit
) {
    Box {
        NetworkImage(
            image = image,
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = contentScale
        )
        val modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(LowPadding)
            .size(30.dp)

        if (withButton)
            if (foodItemUpdateInfo != null && foodItemUpdateInfo.isLoading)
                ShowLoading(progressBarModifier = modifier)
            else
                CircleIconButton(
                    isDrawableIcon = false,
                    icon = if (alreadyWishListed || foodItemUpdateInfo != null && foodItemUpdateInfo.added)
                        Icons.Outlined.Favorite
                    else
                        Icons.Outlined.FavoriteBorder,
                    tint = if (alreadyWishListed || foodItemUpdateInfo != null && foodItemUpdateInfo.added) primaryColor.invoke() else LocalContentColor.current,
                    modifier = modifier
                ) {
                    onclick()
                }
    }
}