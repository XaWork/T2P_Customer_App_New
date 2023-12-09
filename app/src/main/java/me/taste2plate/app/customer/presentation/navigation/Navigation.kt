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
import me.taste2plate.app.customer.presentation.screens.auth.signup.SignUpScreen
import me.taste2plate.app.customer.presentation.screens.bulk_order.BulkOrderScreen
import me.taste2plate.app.customer.presentation.screens.cart.CartScreen
import me.taste2plate.app.customer.presentation.screens.cart.CheckOutViewModel
import me.taste2plate.app.customer.presentation.screens.checkout.CheckoutScreen
import me.taste2plate.app.customer.presentation.screens.checkout.OrderConfirmScreen
import me.taste2plate.app.customer.presentation.screens.citybrand.CityBrandScreen
import me.taste2plate.app.customer.presentation.screens.citybrand.CityBrandViewModel
import me.taste2plate.app.customer.presentation.screens.citybrand.DetailScreen
import me.taste2plate.app.customer.presentation.screens.citybrand.SubCategoryScreen
import me.taste2plate.app.customer.presentation.screens.contact_us.ContactUsScreen
import me.taste2plate.app.customer.presentation.screens.home.CityBrandScreens
import me.taste2plate.app.customer.presentation.screens.home.HomeScreen
import me.taste2plate.app.customer.presentation.screens.location.LocationScreen
import me.taste2plate.app.customer.presentation.screens.membership_plan.MembershipPlanScreen
import me.taste2plate.app.customer.presentation.screens.my_plan.MyPlanScreen
import me.taste2plate.app.customer.presentation.screens.order.OrderDetailsScreen
import me.taste2plate.app.customer.presentation.screens.order.OrderListScreen
import me.taste2plate.app.customer.presentation.screens.order.OrderViewModel
import me.taste2plate.app.customer.presentation.screens.permissions.NotificationPermissionScreen
import me.taste2plate.app.customer.presentation.screens.product.ProductDetailsScreen
import me.taste2plate.app.customer.presentation.screens.product.ProductViewModel
import me.taste2plate.app.customer.presentation.screens.product.list.ProductListScreen
import me.taste2plate.app.customer.presentation.screens.profile.EditProfileScreen
import me.taste2plate.app.customer.presentation.screens.profile.ProfileScreen
import me.taste2plate.app.customer.presentation.screens.profile.ProfileViewModel
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
                },
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

            // ----------------------------> OTP <--------------------------------------
            composable(route = Screens.OTPScreen.route) { entry ->
                val viewModel =
                    entry.sharedViewModel<AuthViewModel>(navHostController = navController)

                OTPScreen(
                    viewModel = viewModel,
                    onNavigateToSignUPScreen = {
                        navController.navigate(Screens.SignUpScreen.route) {
                            popUpTo(0)
                        }
                    }, onNavigateToHomeScreen = {
                        navController.navigate(Screens.HomeScreen.route) {
                            popUpTo(0)
                        }
                    })
            }
        }


        // ----------------------------> Sign Up <--------------------------------------
        composable(route = Screens.SignUpScreen.route) {
            SignUpScreen {
                navController.navigate(Screens.AddEditAddressScreen.route) {
                    popUpTo(0)
                }
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
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("latLng", args)
                    navController.popBackStack()

                   /* navController.navigate(Screens.AddEditAddressScreen.route + "?latLng=$args") {
                        popUpTo(Screens.AddressListScreen.route) {
                            inclusive = true
                        }
                    }*/
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
                    navController.navigate(Screens.OnBoardingScreen.route) {
                        popUpTo(0)
                    }
                },
                onNavigateToCartScreen = {
                    navController.navigate(Screens.CartScreen.route)
                },
                onNavigateToProductListScreen = {
                    val jsonString = Gson().toJson(it, CommonForItem::class.java)
                    navController.navigate(Screens.ProductListScreen.route + "?categoryInfo=$jsonString")
                },
                onNavigateContactUsScreen = {
                    navController.navigate(Screens.ContactUsScreen.route)
                },
                navigateBack = {
                    navController.popBackStack()
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
                    Log.e("product", "product id is $it")
                    navController.navigate(Screens.ProductDetailsScreen.route + "?id=$it")
                },
                onNavigateToWalletScreen = {
                    navController.navigate(Screens.WalletScreen.route)
                },
                onNavigateToWishlistScreen = {
                    navController.navigate(Screens.WishlistScreen.route)
                },
                onNavigateToAddressListScreen = {
                    navController.navigate(Screens.AddressListScreen.route)
                },
                onNavigateToAddAddressScreen = {
                    navController.navigate(Screens.AddEditAddressScreen.route){
                        popUpTo(0)
                    }
                }
            )
        }


        navigation(startDestination = Screens.OrderListScreen.route, route = "order") {
            // ----------------------------> Order List <--------------------------------------
            composable(route = Screens.OrderListScreen.route) { entry ->
                val viewModel =
                    entry.sharedViewModel<OrderViewModel>(navHostController = navController)
                OrderListScreen(
                    viewModel,
                    onNavigateToOrderDetailsScreen = {
                        navController.navigate(Screens.OrderDetailsScreen.route)
                    },
                    navigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            // ----------------------------> Order Details <--------------------------------------
            composable(route = Screens.OrderDetailsScreen.route) { entry ->
                val viewModel =
                    entry.sharedViewModel<OrderViewModel>(navHostController = navController)
                OrderDetailsScreen(viewModel,
                    onNavigateToHomeScreen = {
                        navController.navigate(Screens.HomeScreen.route) {
                            popUpTo(0)
                        }
                    },
                    navigateBack = {
                        navController.popBackStack()
                    })
            }
        }

        // ----------------------------> Notification <--------------------------------------
        composable(route = Screens.NotificationScreen.route) {
            NotificationPermissionScreen(
                onNavigateToHomeScreen = {
                    navController.navigate(Screens.HomeScreen.route)
                }
            )
        }


        // ----------------------------> WishList <--------------------------------------
        composable(route = Screens.WishlistScreen.route) {
            WishlistScreen(
                navigateBack = {
                    navController.popBackStack()
                })
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
            WalletScreen(
                navigateBack = {
                    navController.popBackStack()
                })
        }

        // ----------------------------> Contact Us <--------------------------------------
        composable(route = Screens.ContactUsScreen.route) {
            ContactUsScreen(
                navigateBack = {
                    navController.popBackStack()
                })
        }

        // ----------------------------> My Plan <--------------------------------------
        composable(route = Screens.MyPlanScreen.route) {
            MyPlanScreen(
                navigateBack = {
                    navController.popBackStack()
                })
        }

        // ----------------------------> Bulk Order <--------------------------------------
        composable(route = Screens.BulkOrderScreen.route) {
            BulkOrderScreen {
                navController.popBackStack()
            }
        }

        // ----------------------------> Membership <--------------------------------------
        composable(route = Screens.MembershipScreenScreen.route) {
            MembershipPlanScreen(
                navigateBack = {
                    navController.popBackStack()
                })
        }


        navigation(startDestination = Screens.ProductListScreen.route, route = "product") {

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

                val viewModel =
                    entry.sharedViewModel<ProductViewModel>(navHostController = navController)

                ProductListScreen(
                    viewModel = viewModel,
                    itemInfo = itemInfo,
                    onNavigateToProductDetailsScreen = {
                        navController.navigate(Screens.ProductDetailsScreen.route)
                    },
                    onNavigateToCartScreen = {
                        navController.navigate(Screens.CartScreen.route)
                    },
                    onNavigateToWishlistScreen = {
                        navController.navigate(Screens.WishlistScreen.route)
                    },
                    navigateBack = {
                        navController.popBackStack()
                    })
            }


            // ----------------------------> Product Details <--------------------------------------
            composable(route = Screens.ProductDetailsScreen.route + "?id={id}", arguments = listOf(
                navArgument(name = "id") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                }
            )) { entry ->

                val viewModel =
                    entry.sharedViewModel<ProductViewModel>(navHostController = navController)
                val id = entry.arguments?.getString("id")

                ProductDetailsScreen(
                    productId = id,
                    viewModel = viewModel,
                    onNavigateToCartScreen = {
                        navController.navigate(Screens.CartScreen.route)
                    },
                    navigateBack = {
                        navController.popBackStack()
                    }
                )
            }

        }

        navigation(startDestination = Screens.CityBrandScreen.route, route = "citybrand") {

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
                        navController.navigate(Screens.ProductListScreen.route + "?categoryInfo=$itemInfo")
                    },
                    onNavigateToDetailsScreen = {
                        navController.navigate(Screens.DetailsScreen.route)
                    },
                    onNavigateToSubCategoryScreen = {
                        navController.navigate(Screens.SubCategoryScreen.route)
                    },
                    navigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            // ----------------------------> Details Screen <--------------------------------------
            composable(route = Screens.DetailsScreen.route) { entry ->
                val viewModel =
                    entry.sharedViewModel<CityBrandViewModel>(navHostController = navController)
                DetailScreen(viewModel) {
                    navController.popBackStack()
                }
            }

            // ----------------------------> SubCategory Screen <--------------------------------------
            composable(route = Screens.SubCategoryScreen.route) { entry ->
                val viewModel =
                    entry.sharedViewModel<CityBrandViewModel>(navHostController = navController)
                SubCategoryScreen(viewModel,
                    navigateBack = {
                        navController.popBackStack()
                    }
                ) {
                    val itemInfo = Gson().toJson(
                        it,
                        CommonForItem::class.java
                    )
                    navController.navigate(Screens.ProductListScreen.route + "?categoryInfo=$itemInfo")
                }
            }
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
            composable(route = Screens.CheckoutScreen.route) { entry ->
                val viewModel =
                    entry.sharedViewModel<CheckOutViewModel>(navHostController = navController)
                CheckoutScreen(
                    viewModel = viewModel,
                    onNavigateToOrderConfirmScreen = {
                        navController.navigate(Screens.OrderConfirmScreen.route)
                    },
                    onNavigateToAddressListScreen = {
                        navController.navigate(Screens.AddressListScreen.route)
                    },
                    onNavigateToHomeScreen = {
                        navController.navigate(Screens.HomeScreen.route) {
                            popUpTo(0)
                        }
                    },
                    navigateBack = {
                        navController.popBackStack()
                    })
            }

            // ----------------------------> Order Confirmed <--------------------------------------
            composable(route = Screens.OrderConfirmScreen.route) { entry ->
                val viewModel =
                    entry.sharedViewModel<CheckOutViewModel>(navHostController = navController)
                OrderConfirmScreen(
                    viewModel = viewModel,
                    onNavigateToHomeScreen = {
                        navController.navigate(Screens.HomeScreen.route) {
                            popUpTo(Screens.HomeScreen.route) {
                                inclusive = true
                            }
                        }
                    })
            }
        }



        navigation(startDestination = Screens.ProfileScreen.route, route = "profilenav") {
            // ----------------------------> Profile <--------------------------------------
            composable(route = Screens.ProfileScreen.route) { entry ->
                val viewModel =
                    entry.sharedViewModel<ProfileViewModel>(navHostController = navController)
                ProfileScreen(
                    viewModel = viewModel,
                    onNavigateToEditProfileScreen = {
                        navController.navigate(Screens.EditProfileScreen.route)
                    },
                    onNavigateToAddressListScreen = {
                        navController.navigate(Screens.AddressListScreen.route)
                    },
                    navigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            // ----------------------------> Edit Profile <--------------------------------------
            composable(route = Screens.EditProfileScreen.route) { entry ->
                val viewModel =
                    entry.sharedViewModel<ProfileViewModel>(navHostController = navController)
                EditProfileScreen(
                    viewModel = viewModel,
                ) {
                    navController.navigate(Screens.HomeScreen.route) {
                        popUpTo(0)
                    }
                }
            }
        }




        navigation(startDestination = Screens.AddressListScreen.route, route = "address") {
            // ----------------------------> Address List <--------------------------------------
            composable(route = Screens.AddressListScreen.route) { entry ->
                val viewModel =
                    entry.sharedViewModel<AddressViewModel>(navHostController = navController)
                AddressListScreen(
                    viewModel = viewModel,
                    onNavigateToEditAddEditScreen = {
                        navController.navigate(Screens.AddEditAddressScreen.route)
                    },
                    onPopUpToAddEditScreen = {
                        navController.navigate(Screens.AddEditAddressScreen.route) {
                            popUpTo(0)
                        }
                    },
                    navigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            // ----------------------------> Add Edit Address <--------------------------------------
            composable(route = Screens.AddEditAddressScreen.route/* + "?latLng={latLng}"*//*,
                arguments = listOf(
                    navArgument(name = "latLng") {
                        type = NavType.StringType
                        defaultValue = ""
                        nullable = true
                    }
                )*/) { entry ->
                val latLng =
                    Gson().fromJson(entry.savedStateHandle.get<String>("latLng"), LatLng::class.java)
                val viewModel =
                    entry.sharedViewModel<AddressViewModel>(navHostController = navController)

                AddEditAddressScreen(
                    viewModel = viewModel,
                    latLng = latLng,
                    onNavigateToLocationScreen = { args ->
                        navController.navigate(Screens.LocationScreen.route + "?screen=$args") {
                           // popUpTo(Screens.LocationScreen.route)
                        }
                    },
                    onNavigateToHomeScreen = {
                        navController.navigate(Screens.HomeScreen.route) {
                            popUpTo(0)
                        }
                    },
                    navigateBack = {
                        navController.popBackStack()
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