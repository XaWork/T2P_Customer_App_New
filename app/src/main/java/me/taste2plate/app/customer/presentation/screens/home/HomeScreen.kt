package me.taste2plate.app.customer.presentation.screens.home

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.presentation.screens.home.navigation.DrawerAppScreen
import me.taste2plate.app.customer.presentation.screens.home.navigation.NavigationDrawer
import me.taste2plate.app.customer.presentation.screens.home.widgets.AddressBar
import me.taste2plate.app.customer.presentation.screens.home.widgets.AutoSlidingCarousel
import me.taste2plate.app.customer.presentation.screens.home.widgets.Deals
import me.taste2plate.app.customer.presentation.screens.home.widgets.HeadingChip
import me.taste2plate.app.customer.presentation.screens.home.widgets.HomeAppBar
import me.taste2plate.app.customer.presentation.screens.home.widgets.MostOrderedItemList
import me.taste2plate.app.customer.presentation.screens.home.widgets.SearchBar
import me.taste2plate.app.customer.presentation.screens.home.widgets.SingleFeaturedItem
import me.taste2plate.app.customer.presentation.screens.home.widgets.TopBrands
import me.taste2plate.app.customer.presentation.screens.home.widgets.TopList
import me.taste2plate.app.customer.presentation.screens.home.widgets.TopOrderedFoodCityList
import me.taste2plate.app.customer.presentation.screens.productList
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViews
import me.taste2plate.app.customer.presentation.theme.SpaceBetweenViewsAndSubViews
import me.taste2plate.app.customer.presentation.theme.T2PCustomerAppTheme
import me.taste2plate.app.customer.presentation.widgets.AppScaffold
import me.taste2plate.app.customer.presentation.widgets.DrawableImage
import me.taste2plate.app.customer.presentation.widgets.VerticalSpace

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToOrderScreen: () -> Unit,
    onNavigateToCityBrandScreen: () -> Unit,
    onNavigateToCartScreen: () -> Unit,
    onNavigateToWishlistScreen: () -> Unit,
    onNavigateToProfileScreen: () -> Unit,
    onNavigateToProductListScreen: () -> Unit,
    onNavigateToProductDetailsScreen: () -> Unit,
    onNavigateToBulkOrdersScreen: () -> Unit,
    onNavigateToWalletScreen: () -> Unit,
    onNavigateToMembershipPlanScreen: () -> Unit,
    onNavigateToMyPlanScreen: () -> Unit,
    onNavigateContactUsScreen: () -> Unit,
    onNavigateReferAndEarnScreen: () -> Unit,
    onNavigateLogoutScreen: () -> Unit,
) {
    var searchValue by remember {
        mutableStateOf("")
    }

    val scrollState = rememberLazyListState()
    //  val position by animateFloatAsState(if (scrollUpState.value == true) -150f else 0f, label = "")

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val scope = rememberCoroutineScope()

    NavigationDrawer(drawerState = drawerState, onItemClick = { id ->
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
            DrawerAppScreen.RateApp.name -> {}
            DrawerAppScreen.ReferAndEarn.name -> {}
            DrawerAppScreen.ShareApp.name -> {}
            DrawerAppScreen.ContactUs.name -> {
                onNavigateContactUsScreen()
            }
            DrawerAppScreen.LogOut.name -> {
                onNavigateLogoutScreen()
            }
        }
    }) {
        AppScaffold(
            topBar = {
                HomeAppBar(
                    onNavigateToWishlistScreen = onNavigateToWishlistScreen,
                    onNavigateToCartScreen = onNavigateToCartScreen,
                    onNavigateToSearchScreen = onNavigateToProductListScreen,
                    onNavigationIconClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            },

            floatingActionButton = {
                FloatingActionButton(
                    onClick = { /*TODO*/ },
                    containerColor = Color.Transparent,
                    elevation = FloatingActionButtonDefaults.elevation(0.dp)
                ) {
                    DrawableImage(id = R.drawable.whatsapp_sv)
                }
            }
        ) {
            Column {
//                SearchBar(value = searchValue, onValueChange = {
//                    searchValue = it
//                }) {}
                AddressBar("Address here") {}
                LazyColumn(
                    state = scrollState,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        TopList(
                            onNavigateToCityBrandScreen = {
                                onNavigateToCityBrandScreen()
                            }
                        )
                        AutoSlidingCarousel()
                        VerticalSpace(space = SpaceBetweenViewsAndSubViews)

                        //top order item
                        HeadingChip("Top Ordered Food/City")
                        VerticalSpace(space = SpaceBetweenViewsAndSubViews)
                        TopOrderedFoodCityList(
                            onNavigateToProductListScreen = onNavigateToProductListScreen
                        )
                        VerticalSpace(space = SpaceBetweenViews)

                        //most order item
                        MostOrderedItemList(
                            onNavigateToProductDetailsScreen = onNavigateToProductDetailsScreen
                        )
                        VerticalSpace(space = SpaceBetweenViews)

                        //top brands
                        HeadingChip("Top Brands")
                        VerticalSpace(space = SpaceBetweenViews)
                        TopBrands(
                            onNavigateToProductListScreen = onNavigateToProductListScreen
                        )
                        VerticalSpace(space = SpaceBetweenViewsAndSubViews)

                        //best seller
                        Deals(
                            onNavigateToProductDetailsScreen = onNavigateToProductDetailsScreen
                        )
                        VerticalSpace(space = SpaceBetweenViews)

                        //Featured
                        HeadingChip("Featured")
                        VerticalSpace(space = SpaceBetweenViews)
                    }
                    //Featured
                    items(productList) { product ->
                        SingleFeaturedItem(product, onNavigateToProductDetailsScreen = onNavigateToProductDetailsScreen)
                    }
                }
            }

        }
    }

}

@Preview
@Preview(name = "Dark Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomePreview() {
    T2PCustomerAppTheme {
        // HomeScreen({},{},{},{},{},{},{},{},{},{})
    }
}