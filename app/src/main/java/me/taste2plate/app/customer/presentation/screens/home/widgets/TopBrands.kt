package me.taste2plate.app.customer.presentation.screens.home.widgets

import android.content.res.Configuration
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import me.taste2plate.app.customer.domain.mapper.CommonForItem
import me.taste2plate.app.customer.domain.model.HomeModel
import me.taste2plate.app.customer.presentation.screens.home.CityBrandScreens
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.utils.noRippleClickable
import me.taste2plate.app.customer.presentation.widgets.BlackBorderCard
import me.taste2plate.app.customer.presentation.widgets.ImageWithWishlistButton
import me.taste2plate.app.customer.presentation.widgets.simpleAnimation

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopBrands(
    onNavigateToProductListScreen: (item: CommonForItem) -> Unit,
    autoSlideDuration: Long = 4000L,
    topBrands: List<HomeModel.TopBrand>
) {
    val pagerState = rememberPagerState(pageCount = { topBrands.size })
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
        state = pagerState,
    ) { page ->
        val product = topBrands[page]
        BlackBorderCard(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
            modifier = Modifier
                .noRippleClickable {
                    val item = CommonForItem(
                        id = product.id,
                        name = product.name,
                        image = product.file,
                        type = CityBrandScreens.Brand.name
                    )
                    onNavigateToProductListScreen(item)
                }
                .simpleAnimation(pagerState, page)) {
            ImageWithWishlistButton(image = product.file, withButton = false) {}
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(ScreenPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    product.name,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    minLines = 2
                )
            }
        }
    }

}


@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TopBrandsPreview() {
    T2PCustomerAppTheme {
        // TopBrands({})
    }
}