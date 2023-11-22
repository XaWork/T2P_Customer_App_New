package me.taste2plate.app.customer.presentation.screens.product.list

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.domain.mapper.CommonForItem
import me.taste2plate.app.customer.domain.model.product.ProductListModel
import me.taste2plate.app.customer.presentation.screens.home.widgets.ProductPriceCard
import me.taste2plate.app.customer.presentation.screens.product.ProductViewModel
import me.taste2plate.app.customer.presentation.theme.MediumElevation
import me.taste2plate.app.customer.presentation.theme.MediumRoundedCorners
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.VeryLowSpacing
import me.taste2plate.app.customer.presentation.theme.cardContainerOnSecondaryColor
import me.taste2plate.app.customer.presentation.widgets.AppEmptyView
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.InfoWithIcon
import me.taste2plate.app.customer.presentation.widgets.NetworkImage
import me.taste2plate.app.customer.presentation.widgets.RoundedCornerCard
import me.taste2plate.app.customer.presentation.widgets.ShowLoading
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

@Composable
fun ProductListScreen(
    itemInfo: CommonForItem,
    viewModel: ProductViewModel,
    onNavigateToProductDetailsScreen: () -> Unit
) {
    val state = viewModel.state

    LaunchedEffect(state.productList.isEmpty()) {
        viewModel.onEvent(ProductEvents.GetProducts(itemInfo))
    }

    AppScaffold(
        topBar = {
            AppTopBar(
                title = itemInfo.name,
                tasteVisible = true,
                checked = state.checked,
                onCheckChange = {
                    viewModel.onEvent(ProductEvents.ChangeTaste)
                }
            ) {}
        }
    ) {
        if (state.isLoading)
            ShowLoading(isButton = false)
        else if (state.productList.isEmpty())
            AppEmptyView()
        else
            ContentProductListScreen(
                state,
                onNavigateToProductDetailsScreen = {
                    viewModel.selectedProductId = it
                    onNavigateToProductDetailsScreen()
                }
            )
    }
}

@Composable
fun ContentProductListScreen(
    state: ProductListState,
    onNavigateToProductDetailsScreen: (productId: String) -> Unit
) {

    val items: List<ProductListModel.Result> = state.productList

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(ScreenPadding),
        verticalArrangement = Arrangement.spacedBy(SpaceBetweenViewsAndSubViews)
    ) {
        items(items) { item ->
            SingleProductItem(
                item,
                state =state,
                onNavigateToProductDetailsScreen = {
                    onNavigateToProductDetailsScreen(item.id)
                }
            )
        }
    }
}

@Composable
fun SingleProductItem(
    product: ProductListModel.Result,
    state: ProductListState,
    onNavigateToProductDetailsScreen: () -> Unit
) {
    RoundedCornerCard(
        cardColor = cardContainerOnSecondaryColor.invoke(),
        elevation = MediumElevation,
        modifier = Modifier.clickable {
            onNavigateToProductDetailsScreen()
        }
    ) {
        Column(
            modifier = Modifier.padding(ScreenPadding)
        ) {
            NetworkImage(
                image = product.file[0].location,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(MediumRoundedCorners)),
                contentScale = ContentScale.Crop
            )

            VerticalSpace(space = SpaceBetweenViewsAndSubViews)

            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
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

            VerticalSpace(space = VeryLowSpacing)

            ProductPriceCard(
                price = product.price,
                productId = product.id,
                cartData = state.cartData,
                updateCart = {
                    //updateCart(it)
                })

        }
    }
}


@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ProductListScreenPreview() {
    T2PCustomerAppTheme {
        // ProductListScreen({})
    }
}