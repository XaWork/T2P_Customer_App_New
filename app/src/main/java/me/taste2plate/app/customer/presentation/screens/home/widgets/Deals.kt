package me.taste2plate.app.customer.presentation.screens.home.widgets

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.presentation.screens.productList
import me.taste2plate.app.customer.presentation.theme.LowRoundedCorners
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.utils.rupeeSign
import me.taste2plate.app.customer.presentation.widgets.ImageWithWishlistButton
import me.taste2plate.app.customer.presentation.widgets.InfoWithIcon
import me.taste2plate.app.customer.presentation.widgets.RatingInfoRow
import me.taste2plate.app.customer.presentation.widgets.RedBorderCard
import me.taste2plate.app.customer.presentation.widgets.RoundedCornerIconButton
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Deals(
    title: String = "",
    onNavigateToProductDetailsScreen: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { productList.size })
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.background
                    )
                )
            )
    ) {
        VerticalSpace(space = SpaceBetweenViewsAndSubViews)
        HeadingChipWithLine("Deals")
        VerticalSpace(space = SpaceBetweenViewsAndSubViews)
        HorizontalPager(
            state = pagerState,
        ) {
            SingleBestSellerItem(it, pagerState = pagerState, modifier = Modifier.clickable { onNavigateToProductDetailsScreen() })
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SingleBestSellerItem(
    page: Int,
    modifier: Modifier = Modifier,
    pagerState: PagerState,
) {
    val product = productList[page]
    RedBorderCard(
        modifier = modifier.graphicsLayer {
            val pageOffset: Float =
                (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction.absoluteValue
            translationX = pageOffset * size.width
            alpha = 1 - pageOffset.absoluteValue
        }
    ) {
        Column {
            ImageWithWishlistButton(image = product.image)

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            Column(
                modifier = Modifier.padding(ScreenPadding)
            ) {
                RatingInfoRow(
                    flatOff = "Flat $rupeeSign${product.flatOff} OFF",
                    rating = product.rating
                )

                VerticalSpace(space = SpaceBetweenViews)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(2f)) {
                        Text(
                            text = product.name,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = product.brand,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Light
                        )

                        VerticalSpace(space = SpaceBetweenViewsAndSubViews)

                        InfoWithIcon(
                            icon = true,
                            imageVector = Icons.Outlined.LocationOn,
                            info = product.address
                        )

                        VerticalSpace(space = SpaceBetweenViewsAndSubViews)

                        InfoWithIcon(
                            icon = false,
                            id = R.drawable.delivery_bike,
                            info = product.delivery,
                            colorFilter = ColorFilter.tint(
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                    DealsPriceCard(product.price)
                }

                VerticalSpace(space = SpaceBetweenViews)

            }
        }
    }
}


@Composable
fun DealsPriceCard(
    price: String,
    showAddToCartButton: Boolean = true,
) {
    Column(
        horizontalAlignment = Alignment.End
    ) {
        val annotatedString = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append("$rupeeSign$price")
            }
            withStyle(
                style = SpanStyle()
            ) {
                append("\nFour Places")
            }

        }
        Text(
            text = annotatedString,
            textAlign = TextAlign.End,
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        VerticalSpace(space = SpaceBetweenViews)

        if (showAddToCartButton)
            RoundedCornerIconButton(
                isMaterialIcon = true,
                icon = Icons.Outlined.ShoppingCart,
                cornerRadius = LowRoundedCorners,
                cardColors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {}
    }
}

@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DealsPreview() {
    T2PCustomerAppTheme {
        //Deals({})
    }
}