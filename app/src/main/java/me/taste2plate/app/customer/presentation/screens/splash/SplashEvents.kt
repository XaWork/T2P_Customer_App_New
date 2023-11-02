package me.taste2plate.app.customer.presentation.screens.splash

sealed class SplashEvents {
    object GetSettings : SplashEvents()
    object IsUserLogin : SplashEvents()
}
