package me.taste2plate.app.customer.presentation.screens.cart

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import me.taste2plate.app.customer.presentation.theme.LowRoundedCorners
import me.taste2plate.app.customer.presentation.theme.MediumPadding
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.utils.rupeeSign
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.DrawableIcon
import me.taste2plate.app.customer.presentation.widgets.NetworkImage
import me.taste2plate.app.customer.presentation.widgets.RoundedCornerCard
import me.taste2plate.app.customer.presentation.widgets.SpaceBetweenRow
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

@Composable
fun CartScreen() {
    AppScaffold(
        topBar = {
            AppTopBar {}
        },
    ) {
        ContentCartAndWishlist()
    }
}

@Composable
fun ContentCartAndWishlist(
    isWishList: Boolean = false
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            ScreenPadding
        ),
        verticalArrangement = Arrangement.spacedBy(SpaceBetweenViewsAndSubViews)
    ) {
        items(10) {
            SingleCartAndWishlistItem(
                isWishList = isWishList
            )
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
                .size(70.dp)
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

                Text(text = "${rupeeSign}392")

                if (!isWishList)
                    CartAddRemove()
            }
            SpaceBetweenRow(items = innerItems)
        }

        if (isWishList)
            DrawableIcon(
                id = R.drawable.baseline_cancel_24,
                modifier = Modifier.align(Alignment.Top)
            )

    }

    RoundedCornerCard {
        SpaceBetweenRow(
            modifier = Modifier.padding(MediumPadding),
            items = items
        )
    }
}

@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CartScreenPreview() {
    T2PCustomerAppTheme {
        CartScreen()
    }
}