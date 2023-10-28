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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.presentation.screens.product.CartAddRemove
import me.taste2plate.app.customer.presentation.screens.productList
import me.taste2plate.app.customer.presentation.theme.ExtraHighPadding
import me.taste2plate.app.customer.presentation.theme.LowRoundedCorners
import me.taste2plate.app.customer.presentation.theme.MediumPadding
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.dividerThickness
import me.taste2plate.app.customer.presentation.utils.rupeeSign
import me.taste2plate.app.customer.presentation.widgets.AppButton
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.DrawableIcon
import me.taste2plate.app.customer.presentation.widgets.NetworkImage
import me.taste2plate.app.customer.presentation.widgets.RoundedCornerCard
import me.taste2plate.app.customer.presentation.widgets.SpaceBetweenRow
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

@Composable
fun CartScreen(
    onNavigateToCheckoutScreen: () -> Unit
) {
    AppScaffold(
        topBar = {
            AppTopBar {}
        },
    ) {
        ContentCartAndWishlist(onNavigateToCheckoutScreen = onNavigateToCheckoutScreen)
    }
}

@Composable
fun ContentCartAndWishlist(
    isWishList: Boolean = false,
    onNavigateToCheckoutScreen: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(SpaceBetweenViewsAndSubViews),
            contentPadding = PaddingValues(bottom = ExtraHighPadding)
        ) {
            items(10) {
                SingleCartAndWishlistItem(
                    isWishList = isWishList
                )
            }
        }

        if (!isWishList)
            AppButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(ScreenPadding),
                text = "Confirm Order"
            ) {

                onNavigateToCheckoutScreen()
            }
    }
}


@Composable
fun SingleCartAndWishlistItem(
    isWishList: Boolean
) {
    val items = listOf<@Composable RowScope.() -> Unit> {
        NetworkImage(
            image = productList[0].image,
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
                text = "Product name",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)


            val innerItems = listOf<@Composable RowScope.() -> Unit> {

                if (!isWishList)
                    CartAddRemove()

                Text(text = "${rupeeSign}392")
            }
            SpaceBetweenRow(items = innerItems)
        }

        if (isWishList)
            DrawableIcon(
                id = R.drawable.baseline_cancel_24,
                modifier = Modifier.align(Alignment.Top)
            )

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

@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CartScreenPreview() {
    T2PCustomerAppTheme {
        CartScreen({})
    }
}