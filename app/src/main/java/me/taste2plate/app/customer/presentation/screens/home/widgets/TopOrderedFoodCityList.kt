package me.taste2plate.app.customer.presentation.screens.home.widgets

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.layout.lerp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import me.taste2plate.app.customer.domain.mapper.CommonForItem
import me.taste2plate.app.customer.domain.model.HomeModel
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.utils.noRippleClickable
import me.taste2plate.app.customer.presentation.widgets.simpleAnimation
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopOrderedFoodCityList(
    onNavigateToProductListScreen: (commonItem: CommonForItem) -> Unit,
    autoSlideDuration: Long = 4000L,
    foodItems: List<HomeModel.TopMostOrderedProduct>
) {

    val pagerState = rememberPagerState(pageCount = { foodItems.size })
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

    LaunchedEffect(Unit) {
        while (true) {
            yield()
            delay(autoSlideDuration)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % (pagerState.pageCount),
                animationSpec = tween(600)
            )

        }
    }

    HorizontalPager(
        beyondBoundsPageCount = foodItems
            .size,
        state = pagerState,
    ) { page ->
        val item = foodItems[page]
        SingleTopList(
            image = item.image, modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable {
                    onNavigateToProductListScreen(
                        CommonForItem(
                            id = item.id,
                            image = item.image,
                            name = item.sliderName,
                            type = "slider"
                        )
                    )
                }
                .padding(horizontal = ScreenPadding)
                .height(200.dp)
                .simpleAnimation(pagerState, page)
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