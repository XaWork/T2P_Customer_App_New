package me.taste2plate.app.customer.presentation.screens.home.widgets

import android.content.res.Configuration
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.domain.model.HomeModel
import me.taste2plate.app.customer.domain.model.user.CartModel
import me.taste2plate.app.customer.presentation.screens.home.FoodItemUpdateInfo
import me.taste2plate.app.customer.presentation.screens.home.HomeEvent
import me.taste2plate.app.customer.presentation.screens.home.HomeState
import me.taste2plate.app.customer.presentation.screens.home.HomeViewModel
import me.taste2plate.app.customer.presentation.screens.product.CartAddRemove
import me.taste2plate.app.customer.presentation.theme.ForestGreen
import me.taste2plate.app.customer.presentation.theme.ForestGreenDark
import me.taste2plate.app.customer.presentation.theme.LowPadding
import me.taste2plate.app.customer.presentation.theme.LowRoundedCorners
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.VeryLowSpacing
import me.taste2plate.app.customer.presentation.theme.primaryColor
import me.taste2plate.app.customer.presentation.utils.noRippleClickable
import me.taste2plate.app.customer.presentation.utils.rupeeSign
import me.taste2plate.app.customer.presentation.widgets.ImageWithWishlistButton
import me.taste2plate.app.customer.presentation.widgets.InfoWithIcon
import me.taste2plate.app.customer.presentation.widgets.MaterialIcon
import me.taste2plate.app.customer.presentation.widgets.RedBorderCard
import me.taste2plate.app.customer.presentation.widgets.SpaceBetweenRow
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace
import me.taste2plate.app.customer.presentation.widgets.simpleAnimation

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MostOrderedItemList(
    onNavigateToProductDetailsScreen: (productId: String) -> Unit,
    autoSlideDuration: Long = 4000L,
    viewModel: HomeViewModel
) {
    val state = viewModel.state
    val foodItems = state.homeData!!.mostOrderdItem
    val wishlistItems = state.wishListData!!.result

    val pagerState = rememberPagerState(pageCount = { foodItems.size })
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

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        /*   modifier = Modifier
               .background(
                   brush = Brush.verticalGradient(
                       colors = listOf(
                           MaterialTheme.colorScheme.primary,
                           MaterialTheme.colorScheme.background
                       )
                   )
               )*/
    ) {
        HeadingChipWithLine("Most Ordered Item")
        VerticalSpace(space = VeryLowSpacing)
        HorizontalPager(
            state = pagerState,
        ) { page ->
            val wishlistIdList: List<String> =
                if (wishlistItems.isNotEmpty()) wishlistItems.map { it.product.id }
                    .toList() else emptyList()

            val alreadyInWishlist =
                if (wishlistIdList.isEmpty())
                    false
                else
                    wishlistIdList.contains(foodItems[page].id)


            SingleMostOrderedItem(
                page,
                pagerState = pagerState,
                modifier = Modifier
                    .noRippleClickable { onNavigateToProductDetailsScreen(foodItems[page].id) }
                    .simpleAnimation(pagerState, page),
                state = state,
                product = foodItems[page],
                alreadyWishListed = alreadyInWishlist,
                foodItemUpdateInfo = if (state.foodItemUpdateInfo != null && state.foodItemUpdateInfo.id == foodItems[page].id)
                    state.foodItemUpdateInfo
                else null,
                addToWishlist = {
                    viewModel.onEvent(HomeEvent.AddToWishlist(foodItems[page].id))
                },
                updateCart = {
                    viewModel.onEvent(HomeEvent.UpdateCart(it, foodItems[page].id))
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SingleMostOrderedItem(
    page: Int,
    modifier: Modifier = Modifier,
    state: HomeState,
    pagerState: PagerState,
    product: HomeModel.MostOrderdItem,
    alreadyWishListed: Boolean = false,
    foodItemUpdateInfo: FoodItemUpdateInfo? = null,
    addToWishlist: () -> Unit,
    updateCart: (quantity: Int) -> Unit,
) {
    RedBorderCard(
        modifier = modifier.simpleAnimation(pagerState, page)
    ) {
        Column {
            ImageWithWishlistButton(
                image = product.file[0].location,
                alreadyWishListed = alreadyWishListed,
                foodItemUpdateInfo = foodItemUpdateInfo
            ) {
                addToWishlist()
            }

            VerticalSpace(space = VeryLowSpacing)

            Column(
                modifier = Modifier.padding(ScreenPadding)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!product.sellingPrice.isNullOrEmpty()) {
                        val flatOff = product.sellingPrice.toInt() - product.price.toInt()
                        Text(
                            "Flat $rupeeSign$flatOff OFF",
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }

                    if (product.weight.isNotEmpty())
                        Text(
                            "${product.weight} Kg",
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

                VerticalSpace(space = VeryLowSpacing)

                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    minLines = 2,
                    fontSize = 14.sp
                )

                /*  Text(
                      text = product.brand.name,
                      style = MaterialTheme.typography.titleMedium,
                      fontWeight = FontWeight.Light,
                      maxLines = 2,
                  )

                  VerticalSpace(space = SpaceBetweenViewsAndSubViews)

                  InfoWithIcon(
                      icon = true,
                      imageVector = Icons.Outlined.LocationOn,
                      info = product.city.name
                  )*/

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

                VerticalSpace(space = VeryLowSpacing)


                ProductPriceCard(price = product.price,
                    productId = product.id,
                    cartData = state.cartData,
                    updateCart = {
                        updateCart(it)
                    })
            }
        }
    }
}


@Composable
fun ProductPriceCard(
    productId: String,
    price: String,
    cartData: CartModel? = null,
    updateCart: (quantity: Int) -> Unit
) {

    val items = listOf<@Composable RowScope.() -> Unit> {
        Text(
            "$rupeeSign $price",
            color = primaryColor.invoke(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        //get cart item length
        var cartItemLength = 0
        cartData?.result?.forEach {
            if (it.product.id == productId)
                cartItemLength = it.quantity
        }

        CartAddRemove(
            textInCircleModifier = Modifier.padding(6.dp),
            fontSize = 14.sp,
            textInCircleFontSize = 14.sp,
            cartItemLength = cartItemLength, onUpdateCart = {
                updateCart(it)
            })
    }


    SpaceBetweenRow(items = items)
    /*  Row(
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

                  *//*if (foodItemUpdateInfo != null && !foodItemUpdateInfo.wishlistItem && foodItemUpdateInfo.isLoading)
                    ShowLoading()
                else*//*
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
    }*/
}

@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MostOrderedItemListPreview() {
    T2PCustomerAppTheme {
        //MostOrderedItemList({})
    }
}