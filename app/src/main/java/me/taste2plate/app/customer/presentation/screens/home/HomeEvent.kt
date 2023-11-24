package me.taste2plate.app.customer.presentation.screens.home

import me.taste2plate.app.customer.domain.model.user.address.AddressListModel

sealed class HomeEvent {
    object GetHome : HomeEvent()
    object GetWishlist : HomeEvent()
    object LogOut : HomeEvent()
    object GetAddress : HomeEvent()
    object ChangeTaste : HomeEvent()
    data class AddToWishlist(
        val productId: String,
    ) : HomeEvent()
    data class UpdateCart(
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
