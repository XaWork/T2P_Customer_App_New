package me.taste2plate.app.customer.presentation.screens.splash

import me.taste2plate.app.customer.domain.model.SettingsModel

data class SplashState(
    val loading:Boolean = false,
    val error: String? = null,
    val settings: SettingsModel? = null
)
