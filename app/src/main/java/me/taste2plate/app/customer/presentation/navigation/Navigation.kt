package me.taste2plate.app.customer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.taste2plate.app.customer.presentation.screens.address.AddEditAddressScreen
import me.taste2plate.app.customer.presentation.screens.address.AddressListScreen
import me.taste2plate.app.customer.presentation.screens.auth.onboarding.OnBoardingScreen
import me.taste2plate.app.customer.presentation.screens.auth.otp.OTPScreen
import me.taste2plate.app.customer.presentation.screens.auth.permissions.NotificationPermissionScreen
import me.taste2plate.app.customer.presentation.screens.auth.signin.SignInScreen
import me.taste2plate.app.customer.presentation.screens.auth.signup.SignUpScreen
import me.taste2plate.app.customer.presentation.screens.cart.CartScreen
import me.taste2plate.app.customer.presentation.screens.citybrand.CityBrandScreen
import me.taste2plate.app.customer.presentation.screens.home.HomeScreen
import me.taste2plate.app.customer.presentation.screens.location.LocationScreen
import me.taste2plate.app.customer.presentation.screens.order.OrderDetailsScreen
import me.taste2plate.app.customer.presentation.screens.order.OrderListScreen
import me.taste2plate.app.customer.presentation.screens.product.ProductDetailsScreen
import me.taste2plate.app.customer.presentation.screens.product.ProductListScreen
import me.taste2plate.app.customer.presentation.screens.profile.EditProfileScreen
import me.taste2plate.app.customer.presentation.screens.profile.ProfileScreen
import me.taste2plate.app.customer.presentation.screens.splash.SplashScreen
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
                        popUpTo(Screens.OnBoardingScreen.route) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToHomeScreen = {}
            )
        }

        // ----------------------------> On Boarding <--------------------------------------
        composable(route = Screens.OnBoardingScreen.route) {
            OnBoardingScreen {
                navController.navigate(Screens.OTPScreen.route)
            }
        }


        // ----------------------------> Sign In <--------------------------------------
        composable(route = Screens.SignInScreen.route) {
            SignInScreen {
                navController.navigate(Screens.OTPScreen.route) {
                    /*popUpTo(Screens.OTPScreen.route) {
                        inclusive = true
                    }*/
                }
            }
        }

        // ----------------------------> Sign Up <--------------------------------------
        composable(route = Screens.SignUpScreen.route) {
            SignUpScreen {
                navController.navigate(Screens.LocationScreen.route) {/*
                     popUpTo(Screens.HomeScreen.route) {
                         inclusive = true
                     }*/
                }
            }
        }

        // ----------------------------> OTP <--------------------------------------
        composable(route = Screens.OTPScreen.route) {
            OTPScreen(onNavigateToSignUPScreen = {
                navController.navigate(Screens.SignUpScreen.route)
            }, onNavigateToHomeScreen = {
                navController.navigate(Screens.HomeScreen.route) {
                    popUpTo(Screens.HomeScreen.route) {
                        inclusive = true
                    }
                }
            })
        }

        /*  // ----------------------------> Request Permission <--------------------------------------
          composable(route = Screens.RequestPermissionScreen.route){
              RequestPermissions(permissions = , onNavigateToDeniedContentScreen = { *//*TODO*//* }) {

            }
        }*/


        // ----------------------------> Location <--------------------------------------
        composable(route = Screens.LocationScreen.route) {
            LocationScreen(
                onNavigateToHomeScreen = {
                    navController.navigate(Screens.HomeScreen.route)
                },
                onNavigateToAddEditAddressScreen = {
                    navController.navigate(Screens.AddEditAddressScreen.route)
                },
                onNavigateToNotificationScreen = {
                    navController.navigate(Screens.NotificationScreen.route)
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
                    navController.navigate(Screens.CityBrandScreen.route)
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
                onNavigateContactUsScreen = {},
                onNavigateReferAndEarnScreen = {},
                onNavigateToBulkOrdersScreen = {},
                onNavigateToMembershipPlanScreen = {},
                onNavigateToMyPlanScreen = {},
                onNavigateToProductDetailsScreen = {
                    navController.navigate(Screens.ProductDetailsScreen.route)
                },
                onNavigateToWalletScreen = {},
                onNavigateToWishlistScreen = {
                    navController.navigate(Screens.WishlistScreen.route)
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
            NotificationPermissionScreen {
                navController.navigate(Screens.HomeScreen.route)
            }
        }

        // ----------------------------> WishList <--------------------------------------
        composable(route = Screens.WishlistScreen.route) {
            WishlistScreen()
        }

        // ----------------------------> City brand <--------------------------------------
        composable(route = Screens.CityBrandScreen.route) {
            CityBrandScreen(
                onNavigateToProductListScreen = {
                    navController.navigate(Screens.ProductListScreen.route)
                }
            )
        }

        // ----------------------------> City brand <--------------------------------------
        composable(route = Screens.ProductListScreen.route) {
            ProductListScreen(onNavigateToProductDetailsScreen = {
                navController.navigate(Screens.ProductDetailsScreen.route)
            })
        }

        // ----------------------------> Order Details <--------------------------------------
        composable(route = Screens.OrderDetailsScreen.route) {
            OrderDetailsScreen()
        }

        // ----------------------------> Cart <--------------------------------------
        composable(route = Screens.CartScreen.route) {
            CartScreen()
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

        // ----------------------------> Address List <--------------------------------------
        composable(route = Screens.AddressListScreen.route) {
            AddressListScreen(
                onNavigateToEditAddEditScreen = {
                    navController.navigate(Screens.AddEditAddressScreen.route)
                }
            )
        }

        // ----------------------------> Add Edit Address <--------------------------------------
        composable(route = Screens.AddEditAddressScreen.route) {
            AddEditAddressScreen(
            )
        }
    }
}