package me.taste2plate.app.customer.presentation.navigation


sealed class Screens(val route: String) {
    object SplashScreen : Screens(route = Routes.splashRoute)
    object SignInScreen : Screens(route = Routes.signInRoute)
    object HomeScreen : Screens(route = Routes.homeRoute)

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}