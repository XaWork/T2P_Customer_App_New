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
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.domain.model.HomeModel
import me.taste2plate.app.customer.presentation.screens.home.FoodItemUpdateInfo
import me.taste2plate.app.customer.presentation.screens.home.HomeEvent
import me.taste2plate.app.customer.presentation.screens.home.HomeState
import me.taste2plate.app.customer.presentation.screens.home.HomeViewModel
import me.taste2plate.app.customer.presentation.theme.LowRoundedCorners
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.VeryLowSpacing
import me.taste2plate.app.customer.presentation.utils.rupeeSign
import me.taste2plate.app.customer.presentation.widgets.ImageWithWishlistButton
import me.taste2plate.app.customer.presentation.widgets.InfoWithIcon
import me.taste2plate.app.customer.presentation.widgets.RatingInfoRow
import me.taste2plate.app.customer.presentation.widgets.RedBorderCard
import me.taste2plate.app.customer.presentation.widgets.RoundedCornerIconButton
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace
import me.taste2plate.app.customer.presentation.widgets.simpleAnimation

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Deals(
    onNavigateToProductDetailsScreen: (productId: String) -> Unit,
    viewModel: HomeViewModel
) {

    val state = viewModel.state
    val deals = state.homeData!!.productDeal
    val wishlistItems = state.wishListData!!.result

    val pagerState = rememberPagerState(pageCount = { deals.size })
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
       /* modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.background
                    )
                )
            )*/
    ) {
        HeadingChipWithLine("Deals")
        VerticalSpace(space = VeryLowSpacing)
        HorizontalPager(
            state = pagerState,
        ) { page ->
            val deal = deals[page]
            SingleBestSellerItem(
                page,
                state = state,
                pagerState = pagerState,
                modifier = Modifier.clickable { onNavigateToProductDetailsScreen(deal.id) },
                deal = deal,
                alreadyWishListed = if (wishlistItems.isEmpty()) false else wishlistItems.any { it.product.id == deal.id },
                foodItemUpdateInfo = if (state.foodItemUpdateInfo != null && state.foodItemUpdateInfo.id == deal.id)
                    state.foodItemUpdateInfo
                else null,
                updateCart = {
                    viewModel.onEvent(HomeEvent.UpdateCart(it, deal.id))
                },
                addToWishlist = {
                    viewModel.onEvent(HomeEvent.AddToWishlist(deal.id))
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SingleBestSellerItem(
    page: Int,
    state: HomeState,
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    deal: HomeModel.ProductDeal,
    alreadyWishListed: Boolean = false,
    foodItemUpdateInfo: FoodItemUpdateInfo? = null,
    addToWishlist: () -> Unit,
    updateCart: (quantity: Int) -> Unit
) {
    RedBorderCard(
        modifier = modifier.simpleAnimation(pagerState, page)
    ) {
        Column {
            ImageWithWishlistButton(
                image = deal.file[0].location,
                alreadyWishListed = alreadyWishListed,
                foodItemUpdateInfo = foodItemUpdateInfo
            ) { addToWishlist() }

            VerticalSpace(space = VeryLowSpacing)

            Column(
                modifier = Modifier.padding(ScreenPadding)
            ) {
                RatingInfoRow(
                    flatOff = if (deal.sellingPrice != null && deal.sellingPrice.isNotEmpty())
                        "Flat $rupeeSign${(deal.sellingPrice.toInt() - deal.price.toInt())} OFF"
                    else "",
                    rating = "",
                    weight = deal.weight
                )

                VerticalSpace(space = VeryLowSpacing)

                Text(
                    text = deal.name,
                    maxLines = 2,
                    minLines = 2
                )

                VerticalSpace(space = VeryLowSpacing)

                InfoWithIcon(
                    icon = false,
                    id = R.drawable.delivery_bike,
                    info = "Estimate Delivery",
                    colorFilter = ColorFilter.tint(
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    iconOrImageModifier = Modifier.size(20.dp)

                )

                ProductPriceCard(price = deal.price,
                    productId = deal.id,
                    cartData = state.cartData,
                    updateCart = {
                        updateCart(it)
                    })

            }
        }
    }
}


@Composable
fun DealsPriceCard(
    price: String,
    foodItemUpdateInfo: FoodItemUpdateInfo? = null,
    showAddToCartButton: Boolean = true,
    addToCart: () -> Unit
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
        /*if (foodItemUpdateInfo != null && !foodItemUpdateInfo.wishlistItem && foodItemUpdateInfo.isLoading)
            ShowLoading()
        else*/
            RoundedCornerIconButton(
                isMaterialIcon = true,
                icon = Icons.Outlined.ShoppingCart,
                cornerRadius = LowRoundedCorners,
                cardColors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) { addToCart() }
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