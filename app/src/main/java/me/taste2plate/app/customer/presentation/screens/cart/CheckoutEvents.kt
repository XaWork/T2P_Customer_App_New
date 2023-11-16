package me.taste2plate.app.customer.presentation.screens.cart

import me.taste2plate.app.customer.domain.model.user.address.AddressListModel

sealed class CheckoutEvents {
    object GetCart : CheckoutEvents()
    object GetUser : CheckoutEvents()
    object GetCoupons : CheckoutEvents()
    object GetAddressList : CheckoutEvents()
    object GetDefaultAddress : CheckoutEvents()
    data class SetDefaultAddress(
        val address: AddressListModel.Result
    ) : CheckoutEvents()
    object UpdateState : CheckoutEvents()
    data class UpdateCart(
        val productId: String,
        val quantity: Int
    ) : CheckoutEvents()
}
