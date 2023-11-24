package me.taste2plate.app.customer.presentation.screens.wishlist

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import me.taste2plate.app.customer.domain.mapper.toCommonForWishAndCartItem
import me.taste2plate.app.customer.presentation.screens.cart.ContentCartAndWishlist
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.widgets.AppEmptyView
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.ShowLoading
import me.taste2plate.app.customer.presentation.widgets.showToast

@Composable
fun WishlistScreen(
    viewModel: WishlistViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.onEvent(WishlistEvents.GetWishlist)
    }

    val state = viewModel.state

    LaunchedEffect(state) {
        if (state.normalMessage != null) {
            showToast(state.normalMessage)
            viewModel.onEvent(WishlistEvents.UpdateState)
        }
    }

    AppScaffold(
        topBar = {
            AppTopBar {}
        },
    ) {
        if (state.isLoading)
            ShowLoading(
                isButton = false
            )
        else if (state.wishlist != null && state.wishlist.result.isNotEmpty()) {
            val items = state.wishlist.result.map { it.toCommonForWishAndCartItem() }
            ContentCartAndWishlist(
                isWishList = true,
                items = items,
                removeFromWishlist = {
                    viewModel.onEvent(WishlistEvents.RemoveFromWishlist(it))
                },
                onBackPress = {}
            )
        } else AppEmptyView()
    }
}


@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun WishlistScreenPreview() {
    T2PCustomerAppTheme {
        WishlistScreen()
    }
}