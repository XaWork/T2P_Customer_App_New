package me.taste2plate.app.customer.presentation.screens.wishlist

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.taste2plate.app.customer.presentation.screens.cart.ContentCartAndWishlist
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar

@Composable
fun WishlistScreen() {
    AppScaffold(
        topBar = {
            AppTopBar {}
        },
    ) {
        ContentCartAndWishlist(
            isWishList = true
        )
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