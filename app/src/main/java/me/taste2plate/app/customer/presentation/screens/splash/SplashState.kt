package me.taste2plate.app.customer.presentation.screens.splash

import me.taste2plate.app.customer.domain.model.SettingsModel
import me.taste2plate.app.customer.domain.model.auth.User
import me.taste2plate.app.customer.domain.model.user.address.AddressListModel

data class SplashState(
    val loading: Boolean = false,
    val isLogin: Boolean = false,
    val isError: Boolean = false,
    val user: User? = null,
    val error: String? = null,
    val addressListModel: AddressListModel? = null,
    val settings: SettingsModel? = null
)
