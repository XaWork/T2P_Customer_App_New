package me.taste2plate.app.customer.presentation.screens.wallet

sealed class WalletEvents {
    object GetWalletData : WalletEvents()
    object GetWalletTransaction : WalletEvents()
}
