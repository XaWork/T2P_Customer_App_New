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
import me.taste2plate.app.customer.presentation.screens.bulk_order.BulkOrderScreen
import me.taste2plate.app.customer.presentation.screens.cart.CartScreen
import me.taste2plate.app.customer.presentation.screens.checkout.CheckoutScreen
import me.taste2plate.app.customer.presentation.screens.citybrand.CityBrandScreen
import me.taste2plate.app.customer.presentation.screens.contact_us.ContactUsScreen
import me.taste2plate.app.customer.presentation.screens.home.HomeScreen
import me.taste2plate.app.customer.presentation.screens.location.LocationScreen
import me.taste2plate.app.customer.presentation.screens.membership_plan.MembershipPlanScreen
import me.taste2plate.app.customer.presentation.screens.my_plan.MyPlanScreen
import me.taste2plate.app.customer.presentation.screens.order.OrderDetailsScreen
import me.taste2plate.app.customer.presentation.screens.order.OrderListScreen
import me.taste2plate.app.customer.presentation.screens.product.ProductDetailsScreen
import me.taste2plate.app.customer.presentation.screens.product.ProductListScreen
import me.taste2plate.app.customer.presentation.screens.profile.EditProfileScreen
import me.taste2plate.app.customer.presentation.screens.profile.ProfileScreen
import me.taste2plate.app.customer.presentation.screens.splash.SplashScreen
import me.taste2plate.app.customer.presentation.screens.wallet.WalletScreen
import me.taste2plate.app.customer.presentation.screens.wishlist.WishlistScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.HomeScreen.route) {

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
            CartScreen(onNavigateToCheckoutScreen = {
                navController.navigate(Screens.CheckoutScreen.route)
            })
        }

        // ----------------------------> Product Details <--------------------------------------
        composable(route = Screens.ProductDetailsScreen.route) {
            ProductDetailsScreen(
                onNavigateToCartScreen = {
                    navController.navigate(Screens.CartScreen.route)
                }
            )
        }

        // ----------------------------> Checkout <--------------------------------------
        composable(route = Screens.CheckoutScreen.route) {
            CheckoutScreen()
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