package me.taste2plate.app.customer.presentation.screens.product.list

import android.content.res.Configuration
import android.util.Log
import android.widget.RatingBar
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
import me.taste2plate.app.customer.presentation.screens.home.widgets.AppSearchBar
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
import me.taste2plate.app.customer.presentation.widgets.RatingInfoRow
import me.taste2plate.app.customer.presentation.widgets.RoundedCornerCard
import me.taste2plate.app.customer.presentation.widgets.ShowLoading
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace
import me.taste2plate.app.customer.presentation.widgets.showToast

@Composable
fun ProductListScreen(
    itemInfo: CommonForItem,
    viewModel: ProductViewModel,
    onNavigateToProductDetailsScreen: () -> Unit
) {
    val state = viewModel.state

    LaunchedEffect(Unit) {
        if (itemInfo.name != "Search")
            viewModel.onEvent(ProductEvents.GetProducts(itemInfo))
    }

    LaunchedEffect(key1 = state) {
        if (state.message != null) {
            showToast(state.message)
            viewModel.onEvent(ProductEvents.UpdateState)
        }
    }

    AppScaffold(
        topBar = {
            AppTopBar(
                title = itemInfo.name,
                tasteVisible = itemInfo.name != "Search",
                checked = state.checked,
                onCheckChange = {
                    viewModel.onEvent(ProductEvents.ChangeTaste)
                }
            ) {}
        }
    ) {
        if (state.isLoading)
            ShowLoading(isButton = false)
        else {
            Column {
                if (itemInfo.name == "Search")
                    AppSearchBar(
                        value = viewModel.searchValue,
                        onValueChange = { viewModel.searchValue = it }
                    ) {
                        Log.e("Searc", "onSearch")
                        if (viewModel.searchValue.length >= 3)
                            viewModel.onEvent(ProductEvents.GetProducts(itemInfo))
                    }

                if (state.productList.isEmpty())
                    AppEmptyView()
                else
                    ContentProductListScreen(
                        viewModel = viewModel,
                        onNavigateToProductDetailsScreen = {
                            viewModel.selectedProductId = it
                            onNavigateToProductDetailsScreen()
                        },
                        updateCart = { quantity, productId ->
                            viewModel.onEvent(ProductEvents.UpdateCart(quantity, productId))
                        }
                    )
            }
        }
    }
}

@Composable
fun ContentProductListScreen(
    viewModel: ProductViewModel,
    updateCart: (quantity: Int, productId: String) -> Unit,
    onNavigateToProductDetailsScreen: (productId: String) -> Unit
) {

    val state = viewModel.state
    val items: List<ProductListModel.Result> = state.productList

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(ScreenPadding),
        verticalArrangement = Arrangement.spacedBy(SpaceBetweenViewsAndSubViews)
    ) {
        items(items) { item ->
            SingleProductItem(
                item,
                state = state,
                onNavigateToProductDetailsScreen = {
                    onNavigateToProductDetailsScreen(item.id)
                },
                updateCart = {
                    updateCart(it, item.id)
                }
            )
        }
    }
}

@Composable
fun SingleProductItem(
    product: ProductListModel.Result,
    state: ProductListState,
    updateCart: (quantity: Int) -> Unit,
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

            RatingInfoRow(flatOff = "", rating = "", weight = product.weight)


            VerticalSpace(space = VeryLowSpacing)

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
                    updateCart(it)
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