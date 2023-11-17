package me.taste2plate.app.customer.presentation.screens.home

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.T2PApp
import me.taste2plate.app.customer.domain.model.SettingsModel
import me.taste2plate.app.customer.domain.model.auth.User
import me.taste2plate.app.customer.presentation.screens.address.AddressBottomSheet
import me.taste2plate.app.customer.presentation.screens.home.navigation.DrawerAppScreen
import me.taste2plate.app.customer.presentation.screens.home.navigation.NavigationDrawer
import me.taste2plate.app.customer.presentation.screens.home.widgets.AddressBar
import me.taste2plate.app.customer.presentation.screens.home.widgets.AutoSlidingCarousel
import me.taste2plate.app.customer.presentation.screens.home.widgets.Deals
import me.taste2plate.app.customer.presentation.screens.home.widgets.HeadingChip
import me.taste2plate.app.customer.presentation.screens.home.widgets.HomeAppBar
import me.taste2plate.app.customer.presentation.screens.home.widgets.MostOrderedItemList
import me.taste2plate.app.customer.presentation.screens.home.widgets.SingleFeaturedItem
import me.taste2plate.app.customer.presentation.screens.home.widgets.TopBrands
import me.taste2plate.app.customer.presentation.screens.home.widgets.TopList
import me.taste2plate.app.customer.presentation.screens.home.widgets.TopOrderedFoodCityList
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.DrawableImage
import me.taste2plate.app.customer.presentation.widgets.ShowLoading
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace
import me.taste2plate.app.customer.presentation.widgets.showToast

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToOrderScreen: () -> Unit,
    onNavigateToCityBrandScreen: (screen: CityBrandScreens) -> Unit,
    onNavigateToCartScreen: () -> Unit,
    onNavigateToWishlistScreen: () -> Unit,
    onNavigateToProfileScreen: () -> Unit,
    onNavigateToAddressListScreen: () -> Unit,
    onNavigateToProductListScreen: () -> Unit,
    onNavigateToProductDetailsScreen: (productId: String) -> Unit,
    onNavigateToBulkOrdersScreen: () -> Unit,
    onNavigateToWalletScreen: () -> Unit,
    onNavigateToMembershipPlanScreen: () -> Unit,
    onNavigateToMyPlanScreen: () -> Unit,
    onNavigateContactUsScreen: () -> Unit,
    onNavigateReferAndEarnScreen: () -> Unit,
    onNavigateLogoutScreen: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {

    //obser state
    val state = viewModel.state
    LaunchedEffect(state) {
        when {
            state.message != null && state.addToWishlistResponse != null || state.addToCartResponse != null -> {
                showToast(message = state.message!!)
                viewModel.onEvent(HomeEvent.UpdateState(changeAddToWishlistResponse = true))
            }

            state.addressListModel != null && state.addressListModel.result.isEmpty() -> {

            }
        }
    }
    val context = LocalContext.current
    val scrollState = rememberLazyListState()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    //bottom sheet
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState
        ) {
            if (state.addressListModel != null && state.addressListModel.result.isNotEmpty())
                AddressBottomSheet(
                    isLoading = state.addressLoader,
                    state.addressListModel.result,
                    setDefaultAddress = { address ->
                        showBottomSheet = false
                        viewModel.onEvent(
                            HomeEvent.SetDefaultAddress(address)
                        )
                    },
                    onNavigateToAddressListScreen = {
                        showBottomSheet = false
                        onNavigateToAddressListScreen()
                    }
                )
        }
    }

//Navigation Drawer
    NavigationDrawer(
        userName = state.user?.fullName ?: "",
        drawerState = drawerState, onItemClick = { id ->
            when (id) {
                DrawerAppScreen.Home.name -> {}
                DrawerAppScreen.Orders.name -> {
                    onNavigateToOrderScreen()
                }

                DrawerAppScreen.Profile.name -> {
                    onNavigateToProfileScreen()
                }

                DrawerAppScreen.BulkOrders.name -> {
                    onNavigateToBulkOrdersScreen()
                }

                DrawerAppScreen.Wallet.name -> {
                    onNavigateToWalletScreen()
                }

                DrawerAppScreen.MembershipPlan.name -> {
                    onNavigateToMembershipPlanScreen()
                }

                DrawerAppScreen.MyPlan.name -> {
                    onNavigateToMyPlanScreen()
                }

                DrawerAppScreen.RateApp.name -> {
                    rateApp(context)
                }

                DrawerAppScreen.ReferAndEarn.name -> {
                    if (state.setting != null && state.user != null) {
                        referAndEarn(context, state.setting, state.user)
                    }
                }

                DrawerAppScreen.ShareApp.name -> {}
                DrawerAppScreen.ContactUs.name -> {
                    onNavigateContactUsScreen()
                }

                DrawerAppScreen.LogOut.name -> {
                    viewModel.onEvent(HomeEvent.LogOut)
                    onNavigateLogoutScreen()
                }
            }
        }) {
        AppScaffold(
            topBar = {
                HomeAppBar(
                    onNavigateToWishlistScreen = onNavigateToWishlistScreen,
                    onNavigateToCartScreen = onNavigateToCartScreen,
                    onNavigateToSearchScreen = { },
                    onNavigationIconClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            },

            floatingActionButton = {
                FloatingActionButton(
                    onClick = { openWhatsApp(context) },
                    containerColor = Color.Transparent,
                    elevation = FloatingActionButtonDefaults.elevation(0.dp)
                ) {
                    DrawableImage(id = R.drawable.whatsapp_sv)
                }
            }
        ) {
            if (state.isLoading)
                ShowLoading(
                    isButton = false
                )
            else
                Column {
                    AddressBar(state.defaultAddress!!.address,
                        checked = state.checked,
                        onCheckChange = { viewModel.onEvent(HomeEvent.ChangeTaste) }) {
                        viewModel.onEvent(HomeEvent.GetAddress)
                        showBottomSheet = true

                    }
                    LazyColumn(
                        state = scrollState,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val home = state.homeData
                        item {
                            TopList(
                                onNavigateToCityBrandScreen = { screen ->
                                    onNavigateToCityBrandScreen(screen)
                                }
                            )

                            //slider
                            if (home?.slider != null)
                                CenterColumn {
                                    AutoSlidingCarousel(
                                        pages = home.slider
                                    )
                                    VerticalSpace(space = SpaceBetweenViewsAndSubViews)
                                }


                            //top order item
                            if (home?.topMostOrderedProducts != null) {
                                CenterColumn {
                                    HeadingChip("Top Ordered Food/City")
                                    VerticalSpace(space = SpaceBetweenViewsAndSubViews)
                                    TopOrderedFoodCityList(
                                        onNavigateToProductListScreen = onNavigateToProductListScreen,
                                        home.topMostOrderedProducts
                                    )
                                    VerticalSpace(space = SpaceBetweenViews)
                                }
                            }

                            //most order item
                            if (home?.mostOrderdItem != null)
                                CenterColumn {
                                    MostOrderedItemList(
                                        onNavigateToProductDetailsScreen = {
                                            onNavigateToProductDetailsScreen(it)
                                        },
                                        viewModel = viewModel
                                    )
                                    VerticalSpace(space = SpaceBetweenViews)
                                }

                            //top brands
                            if (home?.topBrands != null)
                                CenterColumn {
                                    HeadingChip("Top Brands")
                                    VerticalSpace(space = SpaceBetweenViews)
                                    TopBrands(
                                        onNavigateToProductListScreen = onNavigateToProductListScreen,
                                        topBrands = home.topBrands
                                    )
                                    VerticalSpace(space = SpaceBetweenViewsAndSubViews)
                                }

                            //Deals
                            if (home?.productDeal != null && home.productDeal.isNotEmpty())
                                CenterColumn {
                                    Deals(
                                        onNavigateToProductDetailsScreen = {
                                            onNavigateToProductDetailsScreen(it)
                                        },
                                        viewModel = viewModel
                                    )
                                    VerticalSpace(space = SpaceBetweenViews)
                                }

                            //Featured
                            if (home?.featured != null)
                                CenterColumn {
                                    HeadingChip("Featured")
                                    VerticalSpace(space = SpaceBetweenViews)
                                }
                        }

                        if (home?.featured != null)
                            items(home.featured) { product ->
                                SingleFeaturedItem(
                                    product,
                                    alreadyWishListed =
                                    if (state.wishListData!!.result.isEmpty()) false
                                    else state.wishListData.result.any { it.product.id == product.id },
                                    foodItemUpdateInfo = if (state.foodItemUpdateInfo != null && state.foodItemUpdateInfo.id == product.id)
                                        state.foodItemUpdateInfo
                                    else null,
                                    onNavigateToProductDetailsScreen = {
                                        onNavigateToProductDetailsScreen(product.id)
                                    },
                                    addToWishlist = {
                                        viewModel.onEvent(HomeEvent.AddToWishlist(product.id))
                                    },
                                    addToCart = {
                                        viewModel.onEvent(HomeEvent.AddToCart(product.id))
                                    }
                                )
                            }
                    }
                }

        }
    }

}

fun rateApp(context: Context) {
    val applicationContext = T2PApp.applicationContext()
    val uri = Uri.parse("market://details?id=" + applicationContext.packageName)
    val goToMarket = Intent(Intent.ACTION_VIEW, uri)
    goToMarket.addFlags(
        Intent.FLAG_ACTIVITY_NO_HISTORY or
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK
    )
    try {
        context.startActivity(goToMarket)
    } catch (e: ActivityNotFoundException) {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://play.google.com/store/apps/details?id=" + applicationContext.packageName)
            )
        )
    }
}

fun referAndEarn(context: Context, setting: SettingsModel.Result, user: User) {
    val pointSettings = setting.point
    val applicationContext = T2PApp.applicationContext()

    val sharingIntent = Intent(Intent.ACTION_SEND)
    sharingIntent.type = "text/plain"
    val shareBody = buildString {
        appendLine("http://play.google.com/store/apps/details?id=" + applicationContext.packageName)
        append(
            "Register with Refer code: ${user.mobile}\n" +
                    "Earn Reward Point: ${pointSettings.signupBonusSender}"
        )
    }
    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here")
    sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
    context.startActivity(
        Intent.createChooser(
            sharingIntent,
            "Share via"
        )
    )
}

@Composable
fun CenterColumn(
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        content()
    }
}

private fun openWhatsApp(context: Context) {
    val intent = Intent(
        Intent.ACTION_VIEW, Uri.parse(
            "https://api.whatsapp.com/send?phone=$+91 8100709627"
        )
    )
    context.startActivity(intent)
}

@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomePreview() {
    T2PCustomerAppTheme {
        // HomeScreen({},{},{},{},{},{},{},{},{},{})
    }
}