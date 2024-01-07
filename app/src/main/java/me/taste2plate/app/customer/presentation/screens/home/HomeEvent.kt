package me.taste2plate.app.customer.presentation.screens.home

import android.content.Context
import me.taste2plate.app.customer.domain.model.custom.LogRequest
import me.taste2plate.app.customer.domain.model.user.address.AddressListModel
import me.taste2plate.app.customer.presentation.screens.auth.AuthEvents

sealed class HomeEvent {
    object GetHome : HomeEvent()
    object CheckDefaultAddress : HomeEvent()
    object GetWishlist : HomeEvent()
    data class AddLog(val logRequest: LogRequest) : HomeEvent()
    object LogOut : HomeEvent()
    object GetAddress : HomeEvent()
    object ChangeTaste : HomeEvent()
    data class AddToWishlist(
        val productId: String,
    ) : HomeEvent()
    data class UpdateCart(
        val context: Context,
        val quantity: Int,
        val productId: String,
    ) : HomeEvent()

    data class SetDefaultAddress(
        val address: AddressListModel.Result,
    ) : HomeEvent()

    data class UpdateState(
        val changeAddToWishlistResponse: Boolean = false,
    ) : HomeEvent()
}
