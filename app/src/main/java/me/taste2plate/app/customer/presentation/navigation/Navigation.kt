package me.taste2plate.app.customer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.taste2plate.app.customer.presentation.screens.auth.onboarding.OnBoardingScreen
import me.taste2plate.app.customer.presentation.screens.auth.otp.OTPScreen
import me.taste2plate.app.customer.presentation.screens.auth.signin.SignInScreen
import me.taste2plate.app.customer.presentation.screens.auth.signup.SignUpScreen
import me.taste2plate.app.customer.presentation.screens.home.HomeScreen
import me.taste2plate.app.customer.presentation.screens.splash.SplashScreen

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
            OnBoardingScreen{
                navController.navigate(Screens.OTPScreen.route) {
                    /*popUpTo(Screens.OTPScreen.route) {
                        inclusive = true
                    }*/
                }
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
                navController.navigate(Screens.OTPScreen.route) {
                   /* popUpTo(Screens.OTPScreen.route) {
                        inclusive = true
                    }*/
                }
            }
        }

        // ----------------------------> OTP <--------------------------------------
        composable(route = Screens.OTPScreen.route) {
            OTPScreen {
                navController.navigate(Screens.HomeScreen.route) {
                    popUpTo(Screens.HomeScreen.route) {
                        inclusive = true
                    }
                }
            }
        }

        // ----------------------------> Home <--------------------------------------
        composable(route = Screens.HomeScreen.route) {
            HomeScreen()
        }
    }
}