package me.taste2plate.app.customer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.taste2plate.app.customer.presentation.screens.auth.signin.SignInScreen
import me.taste2plate.app.customer.presentation.screens.splash.SplashScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.SplashScreen.route) {

        // --------------------------> Splash Screen <------------------------------------
        composable(route = Screens.SplashScreen.route) {
            SplashScreen(
                onNavigateToSignInScreen = {
                    navController.navigate(Screens.SignInScreen.route) {
                        popUpTo(Screens.SignInScreen.route) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToHomeScreen = {}
            )
        }

        // ----------------------------> Sign In <--------------------------------------
        composable(route = Screens.SignInScreen.route) {
            SignInScreen {
                navController.navigate(Screens.HomeScreen.route) {
                    popUpTo(Screens.HomeScreen.route) {
                        inclusive = true
                    }
                }
            }
        }
    }
}