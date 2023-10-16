package me.taste2plate.app.customer.presentation.screens.home.widgets

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.taste2plate.app.customer.presentation.screens.ImageItemList
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.utils.screenWidth

@Composable
fun TopOrderedFoodCityList() {
    val configuration = LocalConfiguration.current
    val screenWidth: Dp = screenWidth.invoke()

    LazyRow(
        modifier = Modifier.height(300.dp)
            .fillMaxWidth()
            .padding(ScreenPadding),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        content = {
            items(ImageItemList) { item ->
                SingleTopList(
                    item, modifier = Modifier
                        .width(screenWidth)
                        .height(300.dp)
                        .padding(bottom = SpaceBetweenViewsAndSubViews)
                )
            }
        })
}


@Preview
@Composable
fun TopOrderedFoodCityListPreview() {
    T2PCustomerAppTheme {
        TopOrderedFoodCityList()
    }
}