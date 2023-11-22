package me.taste2plate.app.customer.presentation.screens.auth

sealed class AuthEvents {
    object Login : AuthEvents()
    object VerifyOTP : AuthEvents()
    object GetUser : AuthEvents()
    object ResendOTP : AuthEvents()
    object SignUp : AuthEvents()
    object UpdateState : AuthEvents()
}
