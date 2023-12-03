package me.taste2plate.app.customer.presentation.screens.cart

import android.content.Context
import me.taste2plate.app.customer.domain.model.user.address.AddressListModel
import me.taste2plate.app.customer.presentation.screens.checkout.DeliveryType
import me.taste2plate.app.customer.presentation.screens.checkout.PaymentType
import me.taste2plate.app.customer.presentation.screens.checkout.TipData

sealed class CheckoutEvents {
    object GetCart : CheckoutEvents()
    object RemoveCoupon : CheckoutEvents()
    object ChangeWalletCheckStatus : CheckoutEvents()
    object GetUser : CheckoutEvents()
    object GetCoupons : CheckoutEvents()
    object GetAddressList : CheckoutEvents()
    object GetDefaultAddress : CheckoutEvents()
    data class Checkout(val context: Context) : CheckoutEvents()
    data class SetDefaultAddress(
        val address: AddressListModel.Result
    ) : CheckoutEvents()

    object ChangeMyPlanValue : CheckoutEvents()
    object CalculateCheckoutDistance : CheckoutEvents()
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

    data class UpdateTip(val index: Int, val otherTipPrice: Int = 0) : CheckoutEvents()

    data class ApplyCoupon(val couponCode: String) : CheckoutEvents()
}
