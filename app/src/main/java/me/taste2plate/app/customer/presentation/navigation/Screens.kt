package me.taste2plate.app.customer.presentation.navigation


sealed class Screens(val route: String) {
    object SplashScreen : Screens(route = Routes.splashRoute)
    object OnBoardingScreen : Screens(route = Routes.onBoardingRoute)
    object SignInScreen : Screens(route = Routes.signInRoute)
    object SignUpScreen : Screens(route = Routes.signUpRoute)
    object HomeScreen : Screens(route = Routes.homeRoute)
    object OTPScreen : Screens(route = Routes.otpRoute)
    object LocationScreen : Screens(route = Routes.locationRoute)
    object OrderListScreen : Screens(route = Routes.orderListRoute)
    object ProfileScreen : Screens(route = Routes.profileRoute)
    object CityBrandScreen : Screens(route = Routes.cityBrandRoute)
    object ProductListScreen : Screens(route = Routes.productListRoute)
    object ProductDetailsScreen : Screens(route = Routes.productDetailsRoute)
    object WishlistScreen : Screens(route = Routes.wishlistRoute)
    object CartScreen : Screens(route = Routes.cartRoute)
    object EditProfileScreen : Screens(route = Routes.editProfileRoute)
    object AddressListScreen : Screens(route = Routes.addressListRoute)
    object AddEditAddressScreen : Screens(route = Routes.addEditAddressRoute)
    object NotificationScreen : Screens(route = Routes.notificationRoute)
    object OrderDetailsScreen : Screens(route = Routes.orderDetailsRoute)
    object RequestPermissionScreen : Screens(route = Routes.requestPermissionRoute)

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}