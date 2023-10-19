package me.taste2plate.app.customer.presentation.screens.home.widgets

import android.content.res.Configuration
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.layout.lerp
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import me.taste2plate.app.customer.presentation.screens.productList
import me.taste2plate.app.customer.presentation.theme.ExtraHighPadding
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.widgets.BlackBorderCard
import me.taste2plate.app.customer.presentation.widgets.ImageWithWishlistButton
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopBrands() {
    val pagerState = rememberPagerState(pageCount = { productList.size })
    HorizontalPager(
        state = pagerState,
    ) { page ->
        val product = productList[page]
        BlackBorderCard(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
            modifier = Modifier.graphicsLayer {
                val pageOffset: Float =
                    (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction.absoluteValue
                translationX = pageOffset * size.width
                lerp(
                    start = ScaleFactor(0.5f, 0.5f),
                    stop = ScaleFactor(1f, 1f),
                    fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f),
                ).also {
                    scaleX = scaleX
                    scaleY = scaleY
                }
                cameraDistance = 8 * density

            }) {
            ImageWithWishlistButton(image = product.image, withButton = false)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = ExtraHighPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    product.brand, style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(product.address, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }

}


@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TopBrandsPreview() {
    T2PCustomerAppTheme {
        TopBrands()
    }
}