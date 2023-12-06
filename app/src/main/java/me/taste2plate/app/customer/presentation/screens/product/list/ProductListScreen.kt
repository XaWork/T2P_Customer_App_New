package me.taste2plate.app.customer.presentation.screens.product.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.taste2plate.app.customer.domain.mapper.CommonForItem
import me.taste2plate.app.customer.domain.model.product.ProductListModel
import me.taste2plate.app.customer.presentation.dialog.SettingDialogType
import me.taste2plate.app.customer.presentation.dialog.SettingInfoDialog
import me.taste2plate.app.customer.presentation.screens.home.FoodItemUpdateInfo
import me.taste2plate.app.customer.presentation.screens.home.HomeEvent
import me.taste2plate.app.customer.presentation.screens.home.widgets.AppSearchBar
import me.taste2plate.app.customer.presentation.screens.home.widgets.ProductPriceCard
import me.taste2plate.app.customer.presentation.screens.product.ProductViewModel
import me.taste2plate.app.customer.presentation.theme.MediumElevation
import me.taste2plate.app.customer.presentation.theme.MediumRoundedCorners
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.VeryLowSpacing
import me.taste2plate.app.customer.presentation.theme.cardContainerOnSecondaryColor
import me.taste2plate.app.customer.presentation.utils.noRippleClickable
import me.taste2plate.app.customer.presentation.widgets.AppEmptyView
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.AppTopBar
import me.taste2plate.app.customer.presentation.widgets.ImageWithWishlistButton
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
    onNavigateToProductDetailsScreen: () -> Unit,
    onNavigateToCartScreen: () -> Unit,
    navigateBack: () -> Unit,
) {
    val state = viewModel.state

    var showSettingDialog by remember {
        mutableStateOf(false)
    }
    if (showSettingDialog) {
        SettingInfoDialog(
            setting = state.settings!!,
            type = SettingDialogType.Cart,
            onDismissRequest = {
                showSettingDialog = false
           viewModel.onEvent(ProductEvents.UpdateState)
            }) {
            showSettingDialog = false
           viewModel.onEvent(ProductEvents.UpdateState)
        }
    }

    LaunchedEffect(Unit) {
        if (itemInfo.name != "Search")
            viewModel.onEvent(ProductEvents.GetProducts(itemInfo))
    }

    LaunchedEffect(key1 = state) {
        if (state.message != null && !state.cartError) {
            showToast(state.message)
            viewModel.onEvent(ProductEvents.UpdateState)
        }
        if (state.cartError)
            showSettingDialog = true
    }

    AppScaffold(
        topBar = {
            AppTopBar(
                title = itemInfo.name,
                tasteVisible = itemInfo.name != "Search",
                checked = state.checked,
                cartVisible = true,
                onNavigateToCartScreen = {
                    onNavigateToCartScreen()
                },
                onCheckChange = {
                    viewModel.onEvent(ProductEvents.ChangeTaste)
                }
            ) { navigateBack() }
        }
    ) {
        if (state.isLoading)
            ShowLoading(isButton = false)
        else {
            ContentProductListScreen(
                viewModel = viewModel,
                itemInfo = itemInfo,
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

@Composable
fun ContentProductListScreen(
    itemInfo: CommonForItem,
    viewModel: ProductViewModel,
    updateCart: (quantity: Int, productId: String) -> Unit,
    onNavigateToProductDetailsScreen: (productId: String) -> Unit
) {

    val state = viewModel.state
    val items: List<ProductListModel.Result> = state.productList

    Column {
        AppSearchBar(
            value = viewModel.searchValue,
            onValueChange = {
                viewModel.searchValue = it
            }
        ) {
            if (itemInfo.name == "Search" && viewModel.searchValue.length >= 3)
                viewModel.onEvent(ProductEvents.GetProducts(itemInfo))
        }

        val modifyList = mutableListOf<ProductListModel.Result>()
        if (viewModel.searchValue.isEmpty()) items.forEach { modifyList.add(it) } else {
            for (item in items) {
                if (item.name.contains(viewModel.searchValue, ignoreCase = true)) {
                    modifyList.add(item)
                }
            }
        }

        if (state.productList.isEmpty())
            AppEmptyView(state.settings!!.productNotAvailableMessage)
        else
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(ScreenPadding),
                verticalArrangement = Arrangement.spacedBy(SpaceBetweenViewsAndSubViews)
            ) {
                items(modifyList) { item ->
                    SingleProductItem(
                        item,
                        state = state,
                        alreadyWishListed = if (state.wishListData!!.result.isEmpty()) false else state.wishListData.result.any { it.product.id == item.id },
                        foodItemUpdateInfo = if (state.foodItemUpdateInfo != null && state.foodItemUpdateInfo.id == item.id)
                            state.foodItemUpdateInfo
                        else null,
                        onNavigateToProductDetailsScreen = {
                            onNavigateToProductDetailsScreen(item.id)
                        },
                        updateCart = {
                            updateCart(it, item.id)
                        },
                        addToWishlist = {
                            viewModel.onEvent(ProductEvents.AddToWishlist(item.id))
                        }
                    )
                }
            }
    }
}

@Composable
fun SingleProductItem(
    product: ProductListModel.Result,
    state: ProductListState,
    alreadyWishListed:Boolean,
    foodItemUpdateInfo: FoodItemUpdateInfo?,
    updateCart: (quantity: Int) -> Unit,
    addToWishlist: () -> Unit,
    onNavigateToProductDetailsScreen: () -> Unit
) {
    RoundedCornerCard(
        cardColor = cardContainerOnSecondaryColor.invoke(),
        elevation = MediumElevation,
        modifier = Modifier.noRippleClickable {
            onNavigateToProductDetailsScreen()
        }
    ) {
        Column(
            modifier = Modifier.padding(ScreenPadding)
        ) {
            ImageWithWishlistButton(
                image = product.file[0].location,
                alreadyWishListed = alreadyWishListed,
                foodItemUpdateInfo = foodItemUpdateInfo,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(MediumRoundedCorners)),
                contentScale = ContentScale.Crop,
                onclick = {
                    addToWishlist()
                }
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
            /*
                        VerticalSpace(space = VeryLowSpacing)

                        InfoWithIcon(
                            icon = false,
                            id = R.drawable.delivery_bike,
                            info = "Estimate Delivery",
                            colorFilter = ColorFilter.tint(
                                color = MaterialTheme.colorScheme.onSurface
                            ),
                            iconOrImageModifier = Modifier.size(20.dp)
                        )*/

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
