package me.taste2plate.app.customer.presentation.screens.home.widgets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.layout.lerp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.taste2plate.app.customer.domain.model.HomeModel
import me.taste2plate.app.customer.presentation.screens.ImageItemList
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopOrderedFoodCityList(
    onNavigateToProductListScreen: () -> Unit,
    foodItems: List<HomeModel.TopMostOrderedProduct>
) {

    val pagerState = rememberPagerState(pageCount = { foodItems.size })

    HorizontalPager(
        state = pagerState,
    ) { page ->
        SingleTopList(
            image = foodItems[page].image, modifier = Modifier
                .fillMaxWidth()
                .clickable {
                        //onNavigateToProductListScreen()
                }
                .padding(horizontal = ScreenPadding)
                .height(300.dp)
                .graphicsLayer {
                    val pageOffset: Float =
                        (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction.absoluteValue
                    lerp(
                        start = ScaleFactor(0.05F, 0.05F),
                        stop = ScaleFactor(1F, 1F),
                        fraction = pageOffset.coerceIn(0f, 1f)
                    )
                }
        )
    }
}


@Preview
@Composable
fun TopOrderedFoodCityListPreview() {
    T2PCustomerAppTheme {
        //TopOrderedFoodCityList({})
    }
}