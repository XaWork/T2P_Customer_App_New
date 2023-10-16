package me.taste2plate.app.customer.presentation.screens.home.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.widgets.DrawableImage

@Composable
fun TopList() {
    val itemList = listOf(
        R.drawable.home_brand, R.drawable.home_category,
        R.drawable.home_city, R.drawable.home_cuisine
    )
    LazyVerticalGrid(
        modifier = Modifier.height(110.dp)
            .padding(ScreenPadding),
        columns = GridCells.Fixed(4),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        content = {
            items(itemList) { item ->
                SingleTopList(item)
            }
        })
}

@Composable
fun SingleTopList(image: Int, modifier: Modifier = Modifier) {
    DrawableImage(
        id = image,
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .height(80.dp),
        contentScale = ContentScale.FillBounds
    )
}

@Preview
@Composable
fun TopListPreview() {
    T2PCustomerAppTheme {
        TopList()
    }
}