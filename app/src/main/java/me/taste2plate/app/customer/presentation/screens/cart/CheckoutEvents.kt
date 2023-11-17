package me.taste2plate.app.customer.presentation.screens.cart

import me.taste2plate.app.customer.domain.model.user.address.AddressListModel
import me.taste2plate.app.customer.presentation.screens.checkout.DeliveryType
import me.taste2plate.app.customer.presentation.screens.checkout.PaymentType

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
    data class ChangeDeliveryType(
        val deliveryType: DeliveryType
    ) : CheckoutEvents()
    data class ChangePaymentType(
        val paymentType: PaymentType
    ) : CheckoutEvents()
    data class UpdateCart(
        val productId: String,
        val quantity: Int
    ) : CheckoutEvents()
}
