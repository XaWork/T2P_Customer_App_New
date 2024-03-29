package me.taste2plate.app.customer.presentation.screens.home

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Discount
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.T2PApp
import me.taste2plate.app.customer.domain.mapper.CommonForItem
import me.taste2plate.app.customer.domain.model.SettingsModel
import me.taste2plate.app.customer.domain.model.auth.User
import me.taste2plate.app.customer.domain.model.custom.LogRequest
import me.taste2plate.app.customer.domain.model.custom.LogType
import me.taste2plate.app.customer.domain.use_case.product.ProductBy
import me.taste2plate.app.customer.presentation.dialog.CustomDialog
import me.taste2plate.app.customer.presentation.dialog.SettingDialogType
import me.taste2plate.app.customer.presentation.dialog.SettingInfoDialog
import me.taste2plate.app.customer.presentation.dialog.SingleCouponItem
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
import me.taste2plate.app.customer.presentation.theme.LowPadding
import me.taste2plate.app.customer.presentation.theme.ScreenPadding
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.theme.VeryLowSpacing
import me.taste2plate.app.customer.presentation.theme.primaryColor
import me.taste2plate.app.customer.presentation.theme.screenBackgroundColor
import me.taste2plate.app.customer.presentation.theme.whatsappColor
import me.taste2plate.app.customer.presentation.utils.noRippleClickable
import me.taste2plate.app.customer.presentation.widgets.AppEmptyView
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.DrawableImage
import me.taste2plate.app.customer.presentation.widgets.HorizontalSpace
import me.taste2plate.app.customer.presentation.widgets.MaterialIcon
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
    onNavigateToAddAddressScreen: () -> Unit,
    onNavigateToProductListScreen: (item: CommonForItem) -> Unit,
    onNavigateToProductDetailsScreen: (productId: String) -> Unit,
    onNavigateToBulkOrdersScreen: () -> Unit,
    onNavigateToWalletScreen: () -> Unit,
    onNavigateToMembershipPlanScreen: () -> Unit,
    onNavigateToMyPlanScreen: () -> Unit,
    onNavigateContactUsScreen: () -> Unit,
    onNavigateToGharKaKhanaBookingScreen: () -> Unit,
    onNavigateAboutScreen: (screen: String) -> Unit,
    navigateBack: () -> Unit,
    onNavigateLogoutScreen: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.state

    val scrollState = rememberLazyListState()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    //bottom sheet
    val scope = rememberCoroutineScope()
    var showAddressSheet by remember { mutableStateOf(false) }
    var showCouponSheet by remember { mutableStateOf(false) }
    val addressSheetState = rememberModalBottomSheetState()
    val couponSheetState = rememberModalBottomSheetState()


    var showCustomDialog by remember {
        mutableStateOf(false)
    }
    var showSettingDialog by remember {
        mutableStateOf(false)
    }
    var showLocationDialog by remember {
        mutableStateOf(false)
    }

    if (showSettingDialog) {
        SettingInfoDialog(
            setting = state.setting!!,
            type = SettingDialogType.Cart,
            onDismissRequest = {
                viewModel.onEvent(HomeEvent.UpdateState())
                showSettingDialog = false
            }) {
            viewModel.onEvent(HomeEvent.UpdateState())
            showSettingDialog = false
        }
    }

    var doubleBackToExitPressedOnce = false
    BackHandler {
        if (doubleBackToExitPressedOnce) {
            navigateBack()
        } else {
            showToast("Please click BACK again to exit")
            doubleBackToExitPressedOnce = true
        }
    }

    //observe state
    LaunchedEffect(state) {
        if (state.noAddressFound) {
            Log.e("Address", "No address found ${state.noAddressFound}")
            //getting white screen issue that's why I use delay
            scope.launch {
                viewModel.onEvent(HomeEvent.UpdateState(changeAddToWishlistResponse = true))
                delay(1000)
                onNavigateToAddAddressScreen()
            }
            //onNavigateToAddAddressScreen()
        }
        if (!state.cartError && state.message != null &&
            state.addToWishlistResponse != null ||
            !state.cartError && state.message != null &&
            state.addToCartResponse != null
        ) {
            showToast(message = state.message)
            viewModel.onEvent(HomeEvent.UpdateState(changeAddToWishlistResponse = true))
        }
        if (state.errorMessage != null && state.isError && state.showErrorMessage) {
            showToast(message = state.errorMessage)
            viewModel.onEvent(HomeEvent.UpdateState(changeAddToWishlistResponse = true))
        }
        if (state.cartError) {
            showSettingDialog = true
        }
        //check app version
        if (state.setting != null) {
            if (!appUpToDate(context, state.setting)) {
                //showCustomDialog = true
            }
        }
    }

    //observer lifecycle
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                Log.e("HomeScreen", "Home Screen on Start")
                viewModel.onEvent(HomeEvent.GetWishlist)
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })


    if (showCustomDialog) {
        CustomDialog(
            title = "Update App",
            text = "Please update your app to get new features.",
            confirmButtonText = "Update",
            dismissAllowed = false
        ) {
            //showCustomDialog = false
            moveToPlayStore(context)
        }
    }

    LaunchedEffect(true) {
        viewModel.onEvent(
            HomeEvent.AddLog(
                LogRequest(
                    type = LogType.pageVisit,
                    event = "enter in home screen",
                    page_name = "/home"
                )
            )
        )
    }

    if (showAddressSheet) {
        ModalBottomSheet(
            containerColor = screenBackgroundColor.invoke(),
            onDismissRequest = {
                showAddressSheet = false
            },
            sheetState = addressSheetState, /*contentColor = screenBackgroundColor.invoke(),*/
        ) {
            // if (state.addressListModel != null && state.addressListModel.result.isNotEmpty())
            AddressBottomSheet(
                isLoading = state.addressLoader,
                addressList = state.addressList,
                defaultAddress = state.defaultAddress,
                setDefaultAddress = { address ->
                    showAddressSheet = false
                    viewModel.onEvent(
                        HomeEvent.SetDefaultAddress(address)
                    )
                },
                onNavigateToAddressListScreen = {
                    showAddressSheet = false
                    onNavigateToAddressListScreen()
                }
            )
        }
    }


    if (showCouponSheet) {
        ModalBottomSheet(
            containerColor = screenBackgroundColor.invoke(),
            onDismissRequest = {
                showCouponSheet = false
            },
            sheetState = couponSheetState,
        ) {
            if (state.couponLoading)
                ShowLoading()
            else if (state.couponList.isEmpty())
                AppEmptyView(text = "No coupon found")
            else
                LazyColumn(modifier = Modifier.padding(ScreenPadding)) {
                    items(state.couponList) { coupon ->
                        SingleCouponItem(coupon = coupon)
                    }
                }
        }
    }

//Navigation Drawer
    NavigationDrawer(
        userName = state.user?.fullName ?: "",
        drawerState = drawerState, onItemClick = { id ->
            when (id) {
                DrawerAppScreen.Home.name -> {}
                DrawerAppScreen.AnyFood2AnyPlace.name -> {
                    onNavigateToGharKaKhanaBookingScreen()
                }

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

                DrawerAppScreen.About.name -> {
                    onNavigateAboutScreen(id)
                }

                DrawerAppScreen.Refund.name -> {
                    onNavigateAboutScreen(id)

                }

                DrawerAppScreen.Terms.name -> {
                    onNavigateAboutScreen(id)
                }

                DrawerAppScreen.Privacy.name -> {
                    onNavigateAboutScreen(id)

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
                    onNavigateToSearchScreen = {
                        val item = CommonForItem(
                            id = "",
                            image = "",
                            name = "Search",
                            type = ProductBy.Search.name
                        )
                        onNavigateToProductListScreen(item)
                    },
                    onNavigationIconClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    onNavigateToWalletScreen = {
                        onNavigateToWalletScreen()
                    }
                )
            },

            floatingActionButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    FloatingActionButton(
                        onClick = {
                            viewModel.onEvent(HomeEvent.GetCoupon)
                            showCouponSheet = true
                        },
                        containerColor = Color.Transparent,
                        elevation = FloatingActionButtonDefaults.elevation(0.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(30.dp))
                                .background(whatsappColor)
                                .padding(7.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            HorizontalSpace(space = ScreenPadding)

                            MaterialIcon(
                                imageVector = Icons.Default.Discount,
                                tint = Color.White
                            )

                            HorizontalSpace(space = VeryLowSpacing)

                            Text("Coupons", color = Color.White)
                        }
                    }

                    FloatingActionButton(
                        onClick = { openWhatsApp(context) },
                        containerColor = Color.Transparent,
                        elevation = FloatingActionButtonDefaults.elevation(0.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(30.dp))
                                .background(whatsappColor)
                                .padding(7.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            DrawableImage(
                                modifier = Modifier.size(25.dp),
                                id = R.drawable.whatsapp_sv
                            )

                            HorizontalSpace(space = VeryLowSpacing)

                            Text("Chat", color = Color.White)
                        }
                    }
                }
            }
        ) {
            if (state.isLoading)
                ShowLoading(
                    isButton = false
                )
            else
                Column {
                    AddressBar(
                        address = state.defaultAddress?.address ?: state.localAddress?.cityName
                        ?: "Your delivery address here...",
                        checked = state.checked,
                        onCheckChange = { viewModel.onEvent(HomeEvent.ChangeTaste) }) {
                        viewModel.onEvent(HomeEvent.GetAddress)
                        showAddressSheet = true
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
                                        modifier = Modifier.noRippleClickable {
                                            // onNavigateToGharKaKhanaBookingScreen()
                                        },
                                        pages = home.slider
                                    )
                                    VerticalSpace(space = VeryLowSpacing)
                                }


                            //top order item
                            if (home?.topMostOrderedProducts != null) {
                                CenterColumn {
                                    HeadingChip(text = "10 Most Ordered Food/City")
                                    VerticalSpace(space = VeryLowSpacing)
                                    TopOrderedFoodCityList(
                                        onNavigateToProductListScreen = {
                                            onNavigateToProductListScreen(it)
                                        },
                                        foodItems = home.topMostOrderedProducts,
                                    )
                                    VerticalSpace(space = VeryLowSpacing)
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
                                }

                            //top brands
                            if (home?.topBrands != null)
                                CenterColumn {
                                    HeadingChip(text = "Top Brands")
                                    VerticalSpace(space = VeryLowSpacing)
                                    TopBrands(
                                        onNavigateToProductListScreen = {
                                            onNavigateToProductListScreen(it)
                                        },
                                        topBrands = home.topBrands
                                    )
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
                                }

                            //Featured
                            if (home?.featured != null)
                                CenterColumn {
                                    HeadingChip(text = "Featured")
                                    VerticalSpace(space = VeryLowSpacing)
                                }
                        }

                        if (home?.featured != null)
                            items(home.featured) { product ->
                                SingleFeaturedItem(
                                    product,
                                    state = state,
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
                                    updateCart = {
                                        viewModel.onEvent(
                                            HomeEvent.UpdateCart(
                                                context,
                                                it,
                                                product.id
                                            )
                                        )
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

private fun appUpToDate(context: Context, state: SettingsModel.Result): Boolean {
    val storedVersion = state.customerAndroidVersion
    val packageManager = context.packageManager
    val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
    val currentVersion = packageInfo.versionName
    Log.e(
        "version",
        "Current App Version : $currentVersion\nStored App version : $storedVersion"
    )
    return storedVersion == currentVersion
}


private fun moveToPlayStore(context: Context) {
    val appPackageName = context.packageName
    try {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=$appPackageName")
            )
        )
    } catch (anfe: ActivityNotFoundException) {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
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