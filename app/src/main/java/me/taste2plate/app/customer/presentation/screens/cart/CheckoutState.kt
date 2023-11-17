package me.taste2plate.app.customer.presentation.screens.cart

import me.taste2plate.app.customer.domain.model.ApplyCouponModel
import me.taste2plate.app.customer.domain.model.CouponModel
import me.taste2plate.app.customer.domain.model.auth.User
import me.taste2plate.app.customer.domain.model.user.CartModel
import me.taste2plate.app.customer.domain.model.user.CommonResponse
import me.taste2plate.app.customer.domain.model.user.address.AddressListModel
import me.taste2plate.app.customer.presentation.screens.checkout.DeliveryType
import me.taste2plate.app.customer.presentation.screens.checkout.PaymentType

data class CheckoutState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val normalMessage: String? = null,
    val cart: CartModel? = null,
    val user: User? = null,
    val deliveryType : DeliveryType = DeliveryType.Standard,
    val paymentType: PaymentType = PaymentType.Online,
    val defaultAddress: AddressListModel.Result? = null,
    val updateCartResponse: CommonResponse? = null,
    val addressList: List<AddressListModel.Result> = emptyList(),
    val couponList: List<CouponModel.Coupon> = emptyList(),
    val applyCouponResponse: ApplyCouponModel? = null,
)
