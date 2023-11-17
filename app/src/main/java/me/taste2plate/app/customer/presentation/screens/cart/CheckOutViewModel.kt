package me.taste2plate.app.customer.presentation.screens.cart

import android.icu.text.DecimalFormat
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.taste2plate.app.customer.T2PApp
import me.taste2plate.app.customer.data.Resource
import me.taste2plate.app.customer.data.Status
import me.taste2plate.app.customer.data.UserPref
import me.taste2plate.app.customer.domain.model.user.address.AddressListModel
import me.taste2plate.app.customer.domain.use_case.ApplyCouponUseCase
import me.taste2plate.app.customer.domain.use_case.CouponByCityUseCase
import me.taste2plate.app.customer.domain.use_case.user.address.AllAddressUseCase
import me.taste2plate.app.customer.domain.use_case.user.cart.CartUseCase
import me.taste2plate.app.customer.domain.use_case.user.cart.DeleteCartUseCase
import me.taste2plate.app.customer.domain.use_case.user.cart.UpdateCartUseCase
import javax.inject.Inject

@HiltViewModel
class CheckOutViewModel @Inject constructor(
    private val userPref: UserPref,
    private val cartUseCase: CartUseCase,
    private val updateCartUseCase: UpdateCartUseCase,
    private val deleteCartUseCase: DeleteCartUseCase,
    private val allAddressUseCase: AllAddressUseCase,
    private val couponByCityUseCase: CouponByCityUseCase,
    private val applyCouponUseCase: ApplyCouponUseCase
) : ViewModel() {

    var state by mutableStateOf(CheckoutState())

    var date by mutableStateOf("")
    var timeSlot by mutableStateOf("")
    var price by mutableDoubleStateOf(0.0)
    var deliveryCharge by mutableDoubleStateOf(0.0)
    var packagingFee by mutableDoubleStateOf(0.0)
    var cgst by mutableDoubleStateOf(0.0)
    var sgst by mutableDoubleStateOf(0.0)
    var igst by mutableDoubleStateOf(0.0)
    var discount by mutableDoubleStateOf(0.0)
    var subscriptionDiscount by mutableDoubleStateOf(0.0)
    var walletDiscount by mutableDoubleStateOf(0.0)
    var totalPrice by mutableDoubleStateOf(0.0)

    init {
        getCart()
    }

    fun onEvent(event: CheckoutEvents) {
        when (event) {
            is CheckoutEvents.GetCart -> {}
            is CheckoutEvents.ChangeDeliveryType -> {
                state = state.copy(deliveryType = event.deliveryType)
            }

            is CheckoutEvents.ChangePaymentType -> {
                state = state.copy(paymentType = event.paymentType)
            }

            is CheckoutEvents.GetUser -> {}
            is CheckoutEvents.GetCoupons -> {
                getCoupons()
            }

            is CheckoutEvents.SetDefaultAddress -> {
                setDefaultAddress(event.address)
            }

            is CheckoutEvents.GetAddressList -> {
                getAddressList()
            }

            is CheckoutEvents.GetDefaultAddress -> {
                setPrice()
                getDefaultAddress()
            }

            is CheckoutEvents.UpdateState -> {
                state = state.copy(
                    normalMessage = null,
                    isError = false,
                    errorMessage = null
                )
            }

            is CheckoutEvents.UpdateCart -> {
                if (event.quantity > 0)
                    updateCart(
                        event.productId,
                        event.quantity
                    )
                else
                    deleteCart(event.productId)
            }
        }
    }

    private fun getCart(
        isLoading: Boolean = true
    ) {
        viewModelScope.launch {
            cartUseCase.execute().collect { result ->
                state = when (result) {
                    is Resource.Loading -> state.copy(isLoading = isLoading)
                    is Resource.Success -> {
                        val data = result.data

                        T2PApp.cartCount = if (data!!.result.isEmpty()) 0 else data.result.size
                        state.copy(
                            isLoading = false,
                            cart = data
                        )
                    }

                    is Resource.Error ->
                        state.copy(
                            isLoading = false,
                            isError = true,
                            errorMessage = result.message
                        )
                }

            }
        }
    }

    private fun setPrice() {
        state.cart!!.apply {
            price = cartprice.toDouble()
            deliveryCharge = shipping.expressShipping.toDouble()
            packagingFee = totalPackingPrice.toDouble()
            cgst = gst.express.totalCgst.toDouble()
            igst = gst.express.totalIgst.toDouble()
            sgst = gst.express.totalSgst.toDouble()
            discount = 0.0
            subscriptionDiscount = planDiscount.toDouble()
            walletDiscount = walletDiscount

            //total
            val decimalFormat = DecimalFormat("#.##")
            totalPrice =
                decimalFormat.format(price + deliveryCharge + packagingFee + cgst + sgst + igst + discount + subscriptionDiscount + walletDiscount)
                    .toDouble()
        }
    }

    private fun updateCart(
        productId: String,
        quantity: Int
    ) {
        viewModelScope.launch {
            updateCartUseCase.execute(
                productId, quantity
            ).collect { result ->
                state = when (result) {
                    is Resource.Loading -> state.copy(isLoading = false)
                    is Resource.Success -> {
                        val data = result.data
                        val isError = data?.status == Status.error.name

                        if (!isError)
                            getCart(isLoading = false)

                        state.copy(
                            isLoading = false,
                            isError = isError,
                            errorMessage = "Something went Wrong",
                            updateCartResponse = data,
                            normalMessage = data?.message,
                        )

                    }

                    is Resource.Error ->
                        state.copy(
                            isLoading = false,
                            isError = true,
                            errorMessage = result.message
                        )
                }

            }
        }
    }

    private fun deleteCart(
        productId: String,
    ) {
        viewModelScope.launch {
            deleteCartUseCase.execute(
                productId
            ).collect { result ->
                state = when (result) {
                    is Resource.Loading -> state.copy(isLoading = false)
                    is Resource.Success -> {
                        val data = result.data
                        val isError = data?.status == Status.error.name

                        if (!isError)
                            getCart(isLoading = false)

                        state.copy(
                            isLoading = false,
                            isError = isError,
                            errorMessage = "Something went Wrong",
                            normalMessage = data?.message,
                            updateCartResponse = data
                        )
                    }

                    is Resource.Error ->
                        state.copy(
                            isLoading = false,
                            isError = true,
                            errorMessage = result.message
                        )
                }

            }
        }
    }

    private fun getDefaultAddress() {
        viewModelScope.launch {
            state = state.copy(defaultAddress = userPref.getDefaultAddress())
            getUser()
        }
        getCoupons()
    }

    private fun setDefaultAddress(address: AddressListModel.Result) {
        viewModelScope.launch {
            state = state.copy(defaultAddress = address)
            getUser()
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            state = state.copy(user = userPref.getUser())
        }
    }

    private fun getAddressList() {
        viewModelScope.launch {
            allAddressUseCase.execute(
            ).collect { result ->
                state = when (result) {
                    is Resource.Loading -> state.copy(isLoading = false)
                    is Resource.Success -> {
                        val data = result.data
                        val isError = data?.status == Status.error.name

                        state.copy(
                            isLoading = false,
                            isError = isError,
                            errorMessage = "Something went Wrong",
                            normalMessage = data?.message,
                            addressList = data?.result ?: emptyList(),
                        )
                    }

                    is Resource.Error ->
                        state.copy(
                            isLoading = false,
                            isError = true,
                            errorMessage = result.message
                        )
                }

            }
        }
    }

    private fun getCoupons() {
        viewModelScope.launch {
            couponByCityUseCase.execute(
                state.defaultAddress!!.city.id
            ).collect { result ->
                state = when (result) {
                    is Resource.Loading -> state.copy(isLoading = false)
                    is Resource.Success -> {
                        val data = result.data
                        val isError = data?.status == Status.error.name

                        if (data != null && data.coupon.isNotEmpty())
                            applyCoupon(data.coupon[0].coupon)

                        state.copy(
                            isLoading = false,
                            isError = isError,
                            errorMessage =
                            if (isError) "Something went Wrong"
                            else if (data!!.coupon.isEmpty()) "No coupon found"
                            else null,
                            couponList = data?.coupon ?: emptyList(),
                        )
                    }

                    is Resource.Error ->
                        state.copy(
                            isLoading = false,
                            isError = true,
                            errorMessage = result.message
                        )
                }

            }
        }
    }

    private fun applyCoupon(couponCode: String) {
        viewModelScope.launch {
            applyCouponUseCase.execute(
                couponCode
            ).collect { result ->
                state = when (result) {
                    is Resource.Loading -> state.copy(isLoading = false)
                    is Resource.Success -> {
                        val data = result.data
                        val isError = data?.status == Status.error.name

                        state.copy(
                            isLoading = false,
                            isError = isError,
                            errorMessage = if (isError) data?.message else null,
                            applyCouponResponse = data,
                        )
                    }

                    is Resource.Error ->
                        state.copy(
                            isLoading = false,
                            isError = true,
                            errorMessage = result.message
                        )
                }

            }
        }
    }


}