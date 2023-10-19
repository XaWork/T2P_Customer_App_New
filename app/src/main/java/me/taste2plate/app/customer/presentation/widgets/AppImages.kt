package me.taste2plate.app.customer.presentation.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.presentation.theme.LowPadding

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
    onclick: () -> Unit
) {
    IconButton(onClick = { onclick() }) {
        Icon(
            modifier = modifier,
            imageVector = imageVector,
            contentDescription = "",
            tint = tint
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
    painterResource: Int,
    modifier: Modifier = Modifier,
    onclick: () -> Unit,
) {
    Card(
        modifier = modifier,
        shape = CircleShape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.outlineVariant
        )
    ) {
        DrawableIconButton(
            modifier = Modifier.padding(LowPadding),
            painterResource = painterResource,
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
    withButton: Boolean = true,
    image: String
) {
    Box {
        NetworkImage(
            image = image,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )
        if (withButton)
            CircleIconButton(
                painterResource = R.drawable.heart,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp)
            ) {}
    }
}