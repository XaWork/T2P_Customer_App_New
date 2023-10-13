package me.taste2plate.app.customer.presentation.navigation


sealed class Screens(val route: String) {
    object SplashScreen : Screens(route = Routes.splashRoute)
    object OnBoardingScreen : Screens(route = Routes.onBoardingRoute)
    object SignInScreen : Screens(route = Routes.signInRoute)
    object SignUpScreen : Screens(route = Routes.signUpRoute)
    object HomeScreen : Screens(route = Routes.homeRoute)
    object OTPScreen : Screens(route = Routes.otpRoute)

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}