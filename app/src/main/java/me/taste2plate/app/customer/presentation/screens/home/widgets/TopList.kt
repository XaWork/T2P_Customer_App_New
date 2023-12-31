package me.taste2plate.app.customer.presentation.screens.home.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.taste2plate.app.customer.presentation.screens.ImageItemList
import me.taste2plate.app.customer.presentation.screens.home.CityBrandScreens
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.utils.noRippleClickable
import me.taste2plate.app.customer.presentation.widgets.DrawableImage
import me.taste2plate.app.customer.presentation.widgets.NetworkImage

@Composable
fun TopList(
    onNavigateToCityBrandScreen: (name: CityBrandScreens) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier
            .height(110.dp)
            .padding(ScreenPadding),
        columns = GridCells.Fixed(4),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        content = {
            itemsIndexed(ImageItemList) { index, item ->
                SingleTopList(drawableImage = item, modifier = Modifier.noRippleClickable {
                    onNavigateToCityBrandScreen(
                        when (index) {
                            0 -> CityBrandScreens.City
                            1 -> CityBrandScreens.Brand
                            2 -> CityBrandScreens.Category
                            3 -> CityBrandScreens.Cuisine
                            else -> CityBrandScreens.City
                        }
                    )
                })
            }
        })
}

@Composable
fun SingleTopList(
    modifier: Modifier = Modifier,
    drawableImage: Int? = null,
    image: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
) {
    if (drawableImage != null)
        DrawableImage(
            id = drawableImage,
            contentScale = contentScale,
            modifier = modifier
                .clip(RoundedCornerShape(10.dp))
                .height(80.dp)
        )
    else
        NetworkImage(
            image = image!!,
            modifier = modifier
                .clip(RoundedCornerShape(10.dp))
                .height(80.dp),
            contentScale = contentScale
        )
}

@Preview
@Composable
fun TopListPreview() {
    T2PCustomerAppTheme {
        //TopList({})
    }
}