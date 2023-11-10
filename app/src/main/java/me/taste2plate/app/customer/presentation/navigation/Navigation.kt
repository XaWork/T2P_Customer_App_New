package me.taste2plate.app.customer.presentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import me.taste2plate.app.customer.domain.mapper.CommonForItem
import me.taste2plate.app.customer.presentation.screens.address.AddEditAddressScreen
import me.taste2plate.app.customer.presentation.screens.address.AddressListScreen
import me.taste2plate.app.customer.presentation.screens.address.AddressViewModel
import me.taste2plate.app.customer.presentation.screens.auth.AuthViewModel
import me.taste2plate.app.customer.presentation.screens.auth.onboarding.OnBoardingScreen
import me.taste2plate.app.customer.presentation.screens.auth.otp.OTPScreen
import me.taste2plate.app.customer.presentation.screens.permissions.NotificationPermissionScreen
import me.taste2plate.app.customer.presentation.screens.auth.signup.SignUpScreen
import me.taste2plate.app.customer.presentation.screens.bulk_order.BulkOrderScreen
import me.taste2plate.app.customer.presentation.screens.cart.CartScreen
import me.taste2plate.app.customer.presentation.screens.cart.CheckOutViewModel
import me.taste2plate.app.customer.presentation.screens.checkout.CheckoutScreen
import me.taste2plate.app.customer.presentation.screens.checkout.OrderConfirmScreen
import me.taste2plate.app.customer.presentation.screens.citybrand.CityBrandScreen
import me.taste2plate.app.customer.presentation.screens.citybrand.CityBrandViewModel
import me.taste2plate.app.customer.presentation.screens.citybrand.DetailScreen
import me.taste2plate.app.customer.presentation.screens.contact_us.ContactUsScreen
import me.taste2plate.app.customer.presentation.screens.home.CityBrandScreens
import me.taste2plate.app.customer.presentation.screens.home.HomeScreen
import me.taste2plate.app.customer.presentation.screens.location.LocationScreen
import me.taste2plate.app.customer.presentation.screens.membership_plan.MembershipPlanScreen
import me.taste2plate.app.customer.presentation.screens.my_plan.MyPlanScreen
import me.taste2plate.app.customer.presentation.screens.order.OrderDetailsScreen
import me.taste2plate.app.customer.presentation.screens.order.OrderListScreen
import me.taste2plate.app.customer.presentation.screens.product.ProductDetailsScreen
import me.taste2plate.app.customer.presentation.screens.product.list.ProductListScreen
import me.taste2plate.app.customer.presentation.screens.profile.EditProfileScreen
import me.taste2plate.app.customer.presentation.screens.profile.ProfileScreen
import me.taste2plate.app.customer.presentation.screens.splash.SplashScreen
import me.taste2plate.app.customer.presentation.screens.wallet.WalletScreen
import me.taste2plate.app.customer.presentation.screens.wishlist.WishlistScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.SplashScreen.route) {

        // --------------------------> Splash Screen <------------------------------------
        composable(route = Screens.SplashScreen.route) {
            SplashScreen(
                onNavigateToOnBoardingScreen = {
                    navController.navigate(Screens.OnBoardingScreen.route) {
                        popUpTo(0)

                    }
                },
                onNavigateToHomeScreen = {
                    navController.navigate(Screens.HomeScreen.route) {
                        popUpTo(0)
                    }
                }
            )
        }

        // ----------------------------> Shared view model for auth screens <--------------------------------------
        navigation(startDestination = Screens.OnBoardingScreen.route, route = "auth") {

            // ----------------------------> On Boarding <--------------------------------------
            composable(route = Screens.OnBoardingScreen.route) { entry ->
                val viewModel =
                    entry.sharedViewModel<AuthViewModel>(navHostController = navController)

                OnBoardingScreen(
                    viewModel = viewModel
                ) {
                    navController.navigate(Screens.OTPScreen.route)
                }
            }

            // ----------------------------> Sign Up <--------------------------------------
            composable(route = Screens.SignUpScreen.route) { entry ->
                val viewModel =
                    entry.sharedViewModel<AuthViewModel>(navHostController = navController)

                SignUpScreen(
                    viewModel = viewModel
                ) {
                    navController.navigate(Screens.LocationScreen.route) {/*
                     popUpTo(Screens.HomeScreen.route) {
                         inclusive = true
                     }*/
                    }
                }
            }

            // ----------------------------> OTP <--------------------------------------
            composable(route = Screens.OTPScreen.route) { entry ->
                val viewModel =
                    entry.sharedViewModel<AuthViewModel>(navHostController = navController)

                OTPScreen(
                    viewModel = viewModel,
                    onNavigateToSignUPScreen = {
                        navController.navigate(Screens.SignUpScreen.route)
                    }, onNavigateToHomeScreen = {
                        navController.navigate(Screens.HomeScreen.route) {
                            popUpTo(0)
                        }
                    })
            }
        }

        /*  // ----------------------------> Request Permission <--------------------------------------
          composable(route = Screens.RequestPermissionScreen.route){
              RequestPermissions(permissions = , onNavigateToDeniedContentScreen = { *//*TODO*//* }) {

            }
        }*/


        // ----------------------------> Location <--------------------------------------
        composable(route = Screens.LocationScreen.route + "?screen={screen}", arguments = listOf(
            navArgument(name = "screen") {
                type = NavType.StringType
                defaultValue = ""
                nullable = true
            }
        )) { entry ->
            val screen = entry.arguments?.getString("screen")
            LocationScreen(
                screen = screen,
                onNavigateToAddEditAddressScreen = {
                    navController.navigate(Screens.AddEditAddressScreen.route)
                },
                onNavigateToNotificationScreen = {
                    navController.navigate(Screens.NotificationScreen.route) {
                        popUpTo(0)
                    }
                },
                onNavigateBackToAddEditAddressScreen = { latLng ->
                    val args = Gson().toJson(latLng, LatLng::class.java)
                    navController.navigate(Screens.AddEditAddressScreen.route + "?latLng=$args") {
                        popUpTo(Screens.AddressListScreen.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // ----------------------------> Home <--------------------------------------
        composable(route = Screens.HomeScreen.route) {
            HomeScreen(
                onNavigateToOrderScreen = {
                    navController.navigate(Screens.OrderListScreen.route)
                },
                onNavigateToCityBrandScreen = {
                    val screen = Gson().toJson(it, CityBrandScreens::class.java)
                    navController.navigate(Screens.CityBrandScreen.withArgs(screen))
                },
                onNavigateToProfileScreen = {
                    navController.navigate(Screens.ProfileScreen.route)
                },
                onNavigateLogoutScreen = {
                    navController.navigate(Screens.OnBoardingScreen.route)
                },
                onNavigateToCartScreen = {
                    navController.navigate(Screens.CartScreen.route)
                },
                onNavigateToProductListScreen = {
                    navController.navigate(Screens.ProductListScreen.route)
                },
                onNavigateContactUsScreen = {
                    navController.navigate(Screens.ContactUsScreen.route)
                },
                onNavigateReferAndEarnScreen = {
                    navController.navigate(Screens.ProductListScreen.route)
                },
                onNavigateToBulkOrdersScreen = {
                    navController.navigate(Screens.BulkOrderScreen.route)
                },
                onNavigateToMembershipPlanScreen = {
                    navController.navigate(Screens.MembershipScreenScreen.route)
                },
                onNavigateToMyPlanScreen = {
                    navController.navigate(Screens.MyPlanScreen.route)
                },
                onNavigateToProductDetailsScreen = {
                    navController.navigate(Screens.ProductDetailsScreen.route)
                },
                onNavigateToWalletScreen = {
                    navController.navigate(Screens.WalletScreen.route)
                },
                onNavigateToWishlistScreen = {
                    navController.navigate(Screens.WishlistScreen.route)
                },
                onNavigateToAddressListScreen = {
                    navController.navigate(Screens.AddressListScreen.route)
                }
            )
        }

        // ----------------------------> Order List <--------------------------------------
        composable(route = Screens.OrderListScreen.route) {
            OrderListScreen(
                onNavigateToOrderDetailsScreen = {
                    navController.navigate(Screens.OrderDetailsScreen.route)
                }
            )
        }

        // ----------------------------> Order List <--------------------------------------
        composable(route = Screens.NotificationScreen.route) {
            NotificationPermissionScreen(
                onNavigateToHomeScreen = {
                    navController.navigate(Screens.HomeScreen.route)
                }
            )
        }

        // ----------------------------> WishList <--------------------------------------
        composable(route = Screens.WishlistScreen.route) {
            WishlistScreen()
        }

        // ----------------------------> Location Permission <--------------------------------------
        /* composable(route = Screens.LocationPermissionScreen.route) {
             LocationPermissionScreen(
                 onNavigateToLocationScreen = {
                     navController.navigate(Screens.LocationScreen.route) {
                         popUpTo(0)
                     }
                 }
             )
         }*/

        // ----------------------------> Wallet <--------------------------------------
        composable(route = Screens.WalletScreen.route) {
            WalletScreen()
        }

        // ----------------------------> Contact Us <--------------------------------------
        composable(route = Screens.ContactUsScreen.route) {
            ContactUsScreen()
        }

        // ----------------------------> My Plan <--------------------------------------
        composable(route = Screens.MyPlanScreen.route) {
            MyPlanScreen()
        }

        // ----------------------------> Bulk Order <--------------------------------------
        composable(route = Screens.BulkOrderScreen.route) {
            BulkOrderScreen()
        }

        // ----------------------------> Membership <--------------------------------------
        composable(route = Screens.MembershipScreenScreen.route) {
            MembershipPlanScreen()
        }

        // ----------------------------> Product List <--------------------------------------
        composable(route = Screens.ProductListScreen.route + "?categoryInfo={categoryInfo}",
            arguments = listOf(
                navArgument("categoryInfo") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                }
            )
        ) { entry ->
            val itemInfo = Gson().fromJson(
                entry.arguments?.getString("categoryInfo"),
                CommonForItem::class.java
            )
            ProductListScreen(
                itemInfo = itemInfo,
                onNavigateToProductDetailsScreen = {
                    navController.navigate(Screens.ProductDetailsScreen.route)
                })
        }


        navigation(startDestination = Screens.CartScreen.route, route = "citybrand") {

            // ----------------------------> City brand <--------------------------------------
            composable(route = Screens.CityBrandScreen.route + "/{screen}", arguments = listOf(
                navArgument(name = "screen") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                }
            )) { entry ->
                val screen =
                    Gson().fromJson(
                        entry.arguments?.getString("screen"),
                        CityBrandScreens::class.java
                    )
                val viewModel =
                    entry.sharedViewModel<CityBrandViewModel>(navHostController = navController)

                CityBrandScreen(
                    viewModel = viewModel,
                    screen = screen,
                    onNavigateToProductListScreen = {
                        val itemInfo = Gson().toJson(
                            it,
                            CommonForItem::class.java
                        )
                        navController.navigate(Screens.ProductListScreen.route+"?categoryInfo=$itemInfo")
                    },
                    onNavigateToDetailsScreen = {
                        navController.navigate(Screens.DetailsScreen.route)
                    }
                )
            }

            // ----------------------------> Details Screen <--------------------------------------
            composable(route = Screens.DetailsScreen.route) { entry ->
                val viewModel =
                    entry.sharedViewModel<CityBrandViewModel>(navHostController = navController)
                DetailScreen(viewModel)
            }
        }


        // ----------------------------> Order Details <--------------------------------------
        composable(route = Screens.OrderDetailsScreen.route) {
            OrderDetailsScreen()
        }


        navigation(startDestination = Screens.CartScreen.route, route = "checkout") {

            // ----------------------------> Cart <--------------------------------------
            composable(route = Screens.CartScreen.route) { entry ->
                val viewModel =
                    entry.sharedViewModel<CheckOutViewModel>(navHostController = navController)

                CartScreen(
                    viewModel = viewModel,
                    onNavigateToCheckoutScreen = {
                        navController.navigate(Screens.CheckoutScreen.route)
                    }, onBackPress = {
                        navController.popBackStack()
                    })
            }


            // ----------------------------> Checkout <--------------------------------------
            composable(route = Screens.CheckoutScreen.route) {
                CheckoutScreen(onNavigateToOrderConfirmScreen = {
                    navController.navigate(Screens.OrderConfirmScreen.route)
                })
            }

            // ----------------------------> Order Confirmed <--------------------------------------
            composable(route = Screens.OrderConfirmScreen.route) {
                OrderConfirmScreen(onNavigateToHomeScreen = {
                    navController.navigate(Screens.HomeScreen.route) {
                        popUpTo(Screens.HomeScreen.route) {
                            inclusive = true
                        }
                    }
                })
            }
        }

        // ----------------------------> Product Details <--------------------------------------
        composable(route = Screens.ProductDetailsScreen.route) {
            ProductDetailsScreen(
                onNavigateToCartScreen = {
                    navController.navigate(Screens.CartScreen.route)
                }
            )
        }


        // ----------------------------> Profile <--------------------------------------
        composable(route = Screens.ProfileScreen.route) {
            ProfileScreen(
                onNavigateToEditProfileScreen = {
                    navController.navigate(Screens.EditProfileScreen.route)
                },
                onNavigateToAddressListScreen = {
                    navController.navigate(Screens.AddressListScreen.route)
                }
            )
        }

        // ----------------------------> Edit Profile <--------------------------------------
        composable(route = Screens.EditProfileScreen.route) {
            EditProfileScreen()
        }



        navigation(startDestination = Screens.CartScreen.route, route = "address") {
            // ----------------------------> Address List <--------------------------------------
            composable(route = Screens.AddressListScreen.route) { entry ->
                val viewModel =
                    entry.sharedViewModel<AddressViewModel>(navHostController = navController)
                AddressListScreen(
                    viewModel = viewModel,
                    onNavigateToEditAddEditScreen = {
                        navController.navigate(Screens.AddEditAddressScreen.route)
                    }
                )
            }

            // ----------------------------> Add Edit Address <--------------------------------------
            composable(route = Screens.AddEditAddressScreen.route + "?latLng={latLng}",
                arguments = listOf(
                    navArgument(name = "latLng") {
                        type = NavType.StringType
                        defaultValue = ""
                        nullable = true
                    }
                )) { entry ->
                val latLng =
                    Gson().fromJson(entry.arguments?.getString("latLng"), LatLng::class.java)
                val viewModel =
                    entry.sharedViewModel<AddressViewModel>(navHostController = navController)

                AddEditAddressScreen(
                    viewModel = viewModel,
                    latLng = latLng,
                    onNavigateToLocationScreen = { args ->
                        navController.navigate(Screens.LocationScreen.route + "?screen=$args") {
                            popUpTo(Screens.LocationScreen.route)
                        }
                    },
                    onNavigateToHomeScreen = {
                        navController.navigate(Screens.HomeScreen.route) {
                            popUpTo(0)
                        }
                    }
                )
            }

        }

    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navHostController: NavHostController
): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navHostController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}