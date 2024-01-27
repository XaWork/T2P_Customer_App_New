package me.taste2plate.app.customer.presentation.screens.cart

import me.taste2plate.app.customer.domain.model.ApplyCouponModel
import me.taste2plate.app.customer.domain.model.CouponModel
import me.taste2plate.app.customer.domain.model.SettingsModel
import me.taste2plate.app.customer.domain.model.auth.User
import me.taste2plate.app.customer.domain.model.product.CalculateCheckoutDistanceModel
import me.taste2plate.app.customer.domain.model.product.CutOffTimeCheckModel
import me.taste2plate.app.customer.domain.model.user.CartModel
import me.taste2plate.app.customer.domain.model.user.CheckoutModel
import me.taste2plate.app.customer.domain.model.user.CommonResponse
import me.taste2plate.app.customer.domain.model.user.MyPlanModel
import me.taste2plate.app.customer.domain.model.user.OrderConfirmModel
import me.taste2plate.app.customer.domain.model.user.address.AddressListModel
import me.taste2plate.app.customer.presentation.screens.checkout.DeliveryType
import me.taste2plate.app.customer.presentation.screens.checkout.PaymentType
import me.taste2plate.app.customer.presentation.screens.checkout.TipData

data class CheckoutState(
    val isLoading: Boolean = false,
    val buttonLoading: Boolean = false,
    val orderConfirmed: Boolean = false,
    val isError: Boolean = false,
    val finish: Boolean = true,
    val errorMessage: String? = null,
    val normalMessage: String? = null,
    val settings: SettingsModel.Result? = null,
    val cart: CartModel? = null,
    val orderConfirmModel: OrderConfirmModel? = null,
    val myPlan: MyPlanModel? = null,
    val appliedCoupon: String? = null,
    val user: User? = null,
    val tips: List<TipData> =
        mutableListOf(
            TipData(tipPrice = 10),
            TipData(tipPrice = 20, mostTipped = true),
            TipData(tipPrice = 30),
            TipData(tipPrice = 0, other = true),
        ),
    val deliveryType: DeliveryType = DeliveryType.Standard,
    val paymentType: PaymentType = PaymentType.Online,
    val defaultAddress: AddressListModel.Result? = null,
    val updateCartResponse: CommonResponse? = null,
    val calculateCheckoutDistanceModel: CalculateCheckoutDistanceModel? = null,
    val cutOffTimeCheckModel: CutOffTimeCheckModel? = null,
    val checkoutModel: CheckoutModel? = null,
    val addressList: List<AddressListModel.Result> = emptyList(),
    val timeSlots: List<String> = emptyList(),
    val couponList: List<CouponModel.Coupon> = emptyList(),
    val applyCouponResponse: ApplyCouponModel? = null,

    //show gif and play sound after coupon applied and order confirmed
    val showCouponGif: Boolean = false,
    val showOrderConfirmGif: Boolean = false,
    var showGifTime: Int = 4,
)
