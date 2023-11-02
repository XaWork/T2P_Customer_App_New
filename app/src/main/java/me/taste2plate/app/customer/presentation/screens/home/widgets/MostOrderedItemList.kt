package me.taste2plate.app.customer.presentation.screens.home.widgets

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.domain.model.HomeModel
import me.taste2plate.app.customer.presentation.screens.productList
import me.taste2plate.app.customer.presentation.theme.ForestGreen
import me.taste2plate.app.customer.presentation.theme.ForestGreenDark
import me.taste2plate.app.customer.presentation.theme.LowPadding
import me.taste2plate.app.customer.presentation.theme.LowRoundedCorners
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.utils.rupeeSign
import me.taste2plate.app.customer.presentation.widgets.ImageWithWishlistButton
import me.taste2plate.app.customer.presentation.widgets.InfoWithIcon
import me.taste2plate.app.customer.presentation.widgets.MaterialIcon
import me.taste2plate.app.customer.presentation.widgets.RedBorderCard
import me.taste2plate.app.customer.presentation.widgets.RoundedCornerIconButton
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MostOrderedItemList(
    onNavigateToProductDetailsScreen: () -> Unit,
    foodItems: List<HomeModel.MostOrderdItem>
) {
    val pagerState = rememberPagerState(pageCount = { foodItems.size })
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
        HeadingChipWithLine("Most Ordered Item")
        VerticalSpace(space = SpaceBetweenViewsAndSubViews)
        HorizontalPager(
            state = pagerState,
        ) {
            SingleMostOrderedItem(
                it,
                pagerState = pagerState,
                modifier = Modifier.clickable { onNavigateToProductDetailsScreen() },
                product = foodItems[it]
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SingleMostOrderedItem(
    page: Int,
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    product: HomeModel.MostOrderdItem
) {
    RedBorderCard(
        modifier = modifier.graphicsLayer {
            val pageOffset: Float =
                (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction.absoluteValue
            translationX = pageOffset * size.width
            alpha = 1 - pageOffset.absoluteValue
        }
    ) {
        Column {
            ImageWithWishlistButton(image = product.file[0].location)

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            Column(
                modifier = Modifier.padding(ScreenPadding)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Flat $rupeeSign${product.discountedPrice} OFF",
                        color = MaterialTheme.colorScheme.tertiary
                    )

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(LowRoundedCorners))
                            .clip(RoundedCornerShape(LowRoundedCorners))
                            .background(
                                color = if (isSystemInDarkTheme()) ForestGreenDark
                                else ForestGreen
                            )
                            .padding(horizontal = LowPadding),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "",
                            color = MaterialTheme.colorScheme.background,
                            fontSize = 14.sp
                        )

                        MaterialIcon(
                            imageVector = Icons.Rounded.Star,
                            tint = MaterialTheme.colorScheme.background,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }

                VerticalSpace(space = SpaceBetweenViews)

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
                    info = product.city
                )

                VerticalSpace(space = SpaceBetweenViewsAndSubViews)

                InfoWithIcon(
                    icon = false,
                    id = R.drawable.delivery_bike,
                    info = "Estimate Delivery",
                    colorFilter = ColorFilter.tint(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )

                VerticalSpace(space = SpaceBetweenViews)

                MostOrderedPriceCard(product.price.toString())
            }
        }
    }
}

@Composable
fun MostOrderedPriceCard(
    price: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Card(
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.primary),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
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
                    append(" Four Places")
                }

            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = annotatedString,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )

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
    }
}

@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MostOrderedItemListPreview() {
    T2PCustomerAppTheme {
        //MostOrderedItemList({})
    }
}