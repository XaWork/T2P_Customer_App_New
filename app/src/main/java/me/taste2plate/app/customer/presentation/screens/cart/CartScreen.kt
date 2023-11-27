package me.taste2plate.app.customer.presentation.screens.cart

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import me.taste2plate.app.customer.domain.mapper.CommonForItem
import me.taste2plate.app.customer.domain.mapper.toCommonForWishAndCartItem
import me.taste2plate.app.customer.presentation.screens.product.CartAddRemove
import me.taste2plate.app.customer.presentation.theme.ExtraHighPadding
import me.taste2plate.app.customer.presentation.theme.LowRoundedCorners
import me.taste2plate.app.customer.presentation.theme.MediumPadding
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.dividerThickness
import me.taste2plate.app.customer.presentation.theme.primaryColor
import me.taste2plate.app.customer.presentation.utils.rupeeSign
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppEmptyView
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.MaterialIconButton
import me.taste2plate.app.customer.presentation.widgets.NetworkImage
import me.taste2plate.app.customer.presentation.widgets.ShowLoading
import me.taste2plate.app.customer.presentation.widgets.SpaceBetweenRow
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace
import me.taste2plate.app.customer.presentation.widgets.showToast

@Composable
fun CartScreen(
    viewModel: CheckOutViewModel,
    onNavigateToCheckoutScreen: () -> Unit,
    onBackPress: () -> Unit,
) {
    val state = viewModel.state

    LaunchedEffect(state) {
        if (state.isError || state.normalMessage != null) {
            showToast(
                message = if (state.isError)
                    state.errorMessage!!
                else state.normalMessage!!
            )

            viewModel.onEvent(CheckoutEvents.UpdateState)
        }
    }

    AppScaffold(
        topBar = {
            AppTopBar {
                onBackPress()
            }
        },
    ) {
        if (state.isLoading)
            ShowLoading(isButton = false)
        else if (state.cart?.result == null || state.cart.result.isEmpty())
            AppEmptyView()
        else {
            val items = state.cart.result.map { it.toCommonForWishAndCartItem() }.toList()
            ContentCartAndWishlist(
                items = items,
                updateCart = { productId, quantity ->
                    viewModel.onEvent(CheckoutEvents.UpdateCart(productId, quantity))
                },
                onNavigateToCheckoutScreen = onNavigateToCheckoutScreen,
                onBackPress = onBackPress
            )
        }
    }
}

@Composable
fun ContentCartAndWishlist(
    isWishList: Boolean = false,
    items: List<CommonForItem>,
    removeFromWishlist: (productId: String) -> Unit = {},
    updateCart: (productId: String, quantity: Int) -> Unit = { _, _ -> },
    onBackPress: () -> Unit,
    onNavigateToCheckoutScreen: () -> Unit = {},
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(SpaceBetweenViewsAndSubViews),
            contentPadding = PaddingValues(bottom = ExtraHighPadding)
        ) {
            items(items) { item ->
                SingleCartAndWishlistItem(
                    isWishList = isWishList,
                    item = item,
                    removeFromWishlist = {
                        removeFromWishlist(item.id)
                    },
                    updateCart = { quantity ->
                        updateCart(item.id, quantity)
                    }
                )
            }
        }

        if (!isWishList) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            ) {
                AppButton(
                    modifier = Modifier
                        .padding(horizontal = ScreenPadding),
                    text = "Continue Shopping"
                ) {
                    onBackPress()
                }

                AppButton(
                    modifier = Modifier
                        .padding(ScreenPadding),
                    text = "Confirm Order"
                ) {
                    onNavigateToCheckoutScreen()
                }
            }
        }
    }
}


@Composable
fun SingleCartAndWishlistItem(
    isWishList: Boolean,
    item: CommonForItem,
    fontSize: TextUnit = TextUnit.Unspecified,
    removeFromWishlist: () -> Unit = {},
    updateCart: (quantity: Int) -> Unit = {}
) {
    val items = listOf<@Composable RowScope.() -> Unit> {
        NetworkImage(
            image = item.image,
            modifier = Modifier
                .size(100.dp)
                .weight(1f)
                .clip(RoundedCornerShape(LowRoundedCorners)),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                .padding(horizontal = SpaceBetweenViewsAndSubViews)
        ) {
            Text(
                text = item.name,
                overflow = TextOverflow.Ellipsis,
                fontSize = fontSize
            )

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            val innerItems = listOf<@Composable RowScope.() -> Unit> {

                if (!isWishList)
                    CartAddRemove(cartItemLength = item.quantity!!) { quantity ->
                        updateCart(quantity)
                    }

                Text(text = "${rupeeSign}${item.price}", fontSize = fontSize)
            }
            SpaceBetweenRow(items = innerItems)
        }

        if (isWishList)
            MaterialIconButton(
                imageVector = Icons.Default.Delete,
                modifier = Modifier.align(Alignment.Top),
                tint = primaryColor.invoke()
            ) {
                removeFromWishlist()
            }

    }


    Box {
        SpaceBetweenRow(
            modifier = Modifier.padding(MediumPadding),
            items = items
        )
        Divider(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(top = SpaceBetweenViewsAndSubViews),
            thickness = dividerThickness
        )
    }
}

/*
@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CartScreenPreview() {
    T2PCustomerAppTheme {
        //CartScreen({})
    }
}*/
