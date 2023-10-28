package me.taste2plate.app.customer.presentation.screens.bulk_order

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.taste2plate.app.customer.presentation.screens.cart.ContentCartAndWishlist
import me.taste2plate.app.customer.presentation.screens.wishlist.WishlistScreen
import me.taste2plate.app.customer.presentation.theme.LowElevation
import me.taste2plate.app.customer.presentation.theme.LowSpacing
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.cardContainerOnSecondaryColor
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTextField
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.RoundedCornerCard

@Composable
fun BulkOrderScreen() {
    AppScaffold(
        topBar = {
            AppTopBar {}
        },
    ) {
        ContentBulkOrderScreen()
    }
}

@Composable
fun ContentBulkOrderScreen() {
    var fullName by remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(ScreenPadding)
    ) {
        RoundedCornerCard(
            cardColor = cardContainerOnSecondaryColor.invoke(),
            elevation = LowElevation
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(ScreenPadding)
            ) {
                AppTextField(
                    modifier = Modifier
                        .padding(bottom = SpaceBetweenViewsAndSubViews),
                    value = fullName, onValueChanged = {
                        fullName = it
                    },
                    hint = "Full name"
                )

                AppTextField(
                    modifier = Modifier
                        .padding(bottom = SpaceBetweenViewsAndSubViews),
                    value = fullName, onValueChanged = {
                        fullName = it
                    },
                    hint = "Email"
                )



                AppTextField(
                    modifier = Modifier
                        .padding(bottom = SpaceBetweenViewsAndSubViews),
                    value = fullName, onValueChanged = {
                        fullName = it
                    },
                    hint = "Mobile number"
                )



                AppTextField(
                    modifier = Modifier
                        .padding(bottom = SpaceBetweenViewsAndSubViews),
                    value = fullName, onValueChanged = {
                        fullName = it
                    },
                    hint = "Address"
                )



                AppTextField(
                    modifier = Modifier
                        .padding(bottom = SpaceBetweenViewsAndSubViews),
                    value = fullName, onValueChanged = {
                        fullName = it
                    },
                    hint = "City"
                )



                AppTextField(
                    modifier = Modifier
                        .padding(bottom = SpaceBetweenViewsAndSubViews)
                        .height(100.dp),
                    value = fullName, onValueChanged = {
                        fullName = it
                    },
                    hint = "Your message"
                )


            }
        }

        AppButton(
            text = "Confirm Order",
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {

        }
    }
}


@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BulkOrderScreenPreview() {
    T2PCustomerAppTheme {
        BulkOrderScreen()
    }
}