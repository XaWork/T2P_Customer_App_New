package me.taste2plate.app.customer.presentation.navigation


sealed class Screens(val route: String) {
    object SplashScreen : Screens(route = Routes.splashRoute)
    object OnBoardingScreen : Screens(route = Routes.onBoardingRoute)
    object SignInScreen : Screens(route = Routes.signInRoute)
    object SignUpScreen : Screens(route = Routes.signUpRoute)
    object HomeScreen : Screens(route = Routes.homeRoute)
    object OTPScreen : Screens(route = Routes.otpRoute)
    object OrderListScreen : Screens(route = Routes.orderListRoute)
    object ProfileScreen : Screens(route = Routes.profileRoute)
    object EditProfileScreen : Screens(route = Routes.editProfileRoute)
    object AddressListScreen : Screens(route = Routes.addressListRoute)
    object AddEditAddressScreen : Screens(route = Routes.addEditAddressRoute)
    object OrderDetailsScreen : Screens(route = Routes.orderDetailsRoute)

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}