package me.taste2plate.app.customer.presentation.screens.wallet

import me.taste2plate.app.customer.domain.model.custom.LogRequest
import me.taste2plate.app.customer.presentation.screens.citybrand.CityBrandEvents

sealed class WalletEvents {
    object GetWalletData : WalletEvents()
    object GetWalletTransaction : WalletEvents()

    data class AddLog(
        val logRequest: LogRequest
    ) : WalletEvents()
}
