package me.taste2plate.app.customer.presentation.screens.home.widgets

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import me.taste2plate.app.customer.domain.model.HomeModel
import me.taste2plate.app.customer.presentation.screens.productList
import me.taste2plate.app.customer.presentation.theme.ExtraHighPadding
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.widgets.BlackBorderCard
import me.taste2plate.app.customer.presentation.widgets.ImageWithWishlistButton
import me.taste2plate.app.customer.presentation.widgets.simpleAnimation
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopBrands(
    onNavigateToProductListScreen: () -> Unit,
    topBrands: List<HomeModel.TopBrand>
) {
    val pagerState = rememberPagerState(pageCount = { topBrands.size })
    HorizontalPager(
        state = pagerState,
    ) { page ->
        val product = topBrands[page]
        BlackBorderCard(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
            modifier = Modifier
                .clickable { onNavigateToProductListScreen() }
                .simpleAnimation(pagerState, page)) {
            ImageWithWishlistButton(image = product.file, withButton = false){}
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = ExtraHighPadding,
                        horizontal = ScreenPadding
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    product.name, style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    minLines = 2
                )
                Text(
                    "City Name", style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1
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