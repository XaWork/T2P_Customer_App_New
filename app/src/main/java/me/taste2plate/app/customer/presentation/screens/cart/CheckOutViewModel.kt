package me.taste2plate.app.customer.presentation.screens.cart

import android.icu.text.DecimalFormat
import android.util.Log
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
import me.taste2plate.app.customer.domain.model.user.MyPlanModel
import me.taste2plate.app.customer.domain.model.user.address.AddressListModel
import me.taste2plate.app.customer.domain.use_case.ApplyCouponUseCase
import me.taste2plate.app.customer.domain.use_case.CouponByCityUseCase
import me.taste2plate.app.customer.domain.use_case.product.CutOffTimeCheckUseCase
import me.taste2plate.app.customer.domain.use_case.user.MyPlanUseCase
import me.taste2plate.app.customer.domain.use_case.user.address.AllAddressUseCase
import me.taste2plate.app.customer.domain.use_case.user.cart.CartUseCase
import me.taste2plate.app.customer.domain.use_case.user.cart.DeleteCartUseCase
import me.taste2plate.app.customer.domain.use_case.user.cart.UpdateCartUseCase
import me.taste2plate.app.customer.presentation.screens.checkout.DeliveryType
import me.taste2plate.app.customer.presentation.screens.checkout.PaymentType
import javax.inject.Inject

@HiltViewModel
class CheckOutViewModel @Inject constructor(
    private val userPref: UserPref,
    private val cartUseCase: CartUseCase,
    private val updateCartUseCase: UpdateCartUseCase,
    private val deleteCartUseCase: DeleteCartUseCase,
    private val allAddressUseCase: AllAddressUseCase,
    private val cutOffTimeCheckUseCase: CutOffTimeCheckUseCase,
    private val couponByCityUseCase: CouponByCityUseCase,
    private val applyCouponUseCase: ApplyCouponUseCase,
    private val myPlanUseCase: MyPlanUseCase
) : ViewModel() {

    var state by mutableStateOf(CheckoutState())

    val decimalFormat = DecimalFormat("#.##")
    var walletChecked by mutableStateOf(false)
    var walletEnabled by mutableStateOf(false)
    var showDialogExpress by mutableStateOf(false)
    var showOrderAmountDialog by mutableStateOf(false)
    var showDigitalCODDialog by mutableStateOf(false)
    var showOrderAmountDialogMessage by mutableStateOf("")
    var codEnabled by mutableStateOf(true)
    var expressEnabled by mutableStateOf(true)

    var selectedDate by mutableStateOf("")
    var selectedTimeSlot by mutableStateOf("")
    var pointConversionText by mutableStateOf("")
    var price by mutableDoubleStateOf(0.0)
    var deliveryCharge by mutableDoubleStateOf(0.0)
    var packagingFee by mutableDoubleStateOf(0.0)
    var cgst by mutableDoubleStateOf(0.0)
    var sgst by mutableDoubleStateOf(0.0)
    var igst by mutableDoubleStateOf(0.0)
    var couponDiscount by mutableDoubleStateOf(0.0)
    var subscriptionDiscount by mutableDoubleStateOf(0.0)
    var discountWallet by mutableDoubleStateOf(0.0)
    var totalPrice by mutableDoubleStateOf(0.0)
    var minOrder: String = ""
    var maxOrder: String = ""
    var walletPoint: String = ""

    init {
        getCart()
        getSettings()
    }

    fun onEvent(event: CheckoutEvents) {
        when (event) {
            is CheckoutEvents.GetCart -> {}
            is CheckoutEvents.ChangeMyPlanValue -> {
                state = state.copy(myPlan = null)
            }

            is CheckoutEvents.ChangeWalletCheckStatus -> {
                walletChecked = !walletChecked
                setPriceAfterWalletStatusChange()
            }

            is CheckoutEvents.ApplyCoupon -> {
                applyCoupon(event.couponCode)
            }

            is CheckoutEvents.ChangeDeliveryType -> {
                state = state.copy(deliveryType = event.deliveryType)
                setPriceAfterChangeDeliveryType()

            }

            is CheckoutEvents.ChangePaymentType -> {
                state = state.copy(paymentType = event.paymentType)
            }

            is CheckoutEvents.GetUser -> {}

            is CheckoutEvents.UpdateTip -> {
                val updatedTipList = state.tips.mapIndexed { i, tip ->
                    if (i == event.index) {
                        tip.copy(selected = !tip.selected)
                    } else {
                        tip.copy(selected = false)
                    }
                }

                state = state.copy(tips = updatedTipList)
            }

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

    private fun checkPriceRequirements() {
        val priceToCheck = if (state.deliveryType == DeliveryType.Express) {
            state.cart!!.newFinalPrice.express.toFloat()
        } else {
            state.cart!!.newFinalPrice.normal.toFloat()
        }
        val minPrice = state.settings!!.minimumOrderValue.toFloat()
        val maxPrice = state.settings!!.maximumOrderValueCod.toFloat()

        when (state.paymentType) {
            PaymentType.Online -> {
                if (priceToCheck < minPrice) {
                    showOrderAmountDialog = true
                    showOrderAmountDialogMessage =
                        "Online payment order can be placed for minimum amount of Rs. $minPrice"
                }
            }

            PaymentType.COD -> {
                if (priceToCheck < minPrice) {
                    showOrderAmountDialogMessage =
                        "COD order can be placed for amount between Rs. $minPrice - Rs. $maxPrice"
                    showOrderAmountDialog = true
                } else {
                    showDigitalCODDialog = true
                }
            }
        }
    }

    private fun setPriceAfterChangeDeliveryType() {
        selectedDate = ""
        selectedTimeSlot = ""
        when {
            state.applyCouponResponse != null -> {
                if (state.deliveryType == DeliveryType.Standard) {
                    val couponResponse = state.applyCouponResponse!!
                    deliveryCharge = couponResponse.shipping.normalShipping.toDouble()

                    val tPrice =
                        if (walletChecked) couponResponse.new_final_price.withWallet!!.normal
                        else couponResponse.new_final_price.normal
                    totalPrice = decimalFormat.format(tPrice.toDouble()).toDouble()

                    igst =
                        if (walletChecked) couponResponse.gstWithWallet.normal.totalIgst.toDouble()
                        else couponResponse.gst.normal.totalIgst.toDouble()
                    sgst =
                        if (walletChecked) couponResponse.gstWithWallet.normal.totalSgst.toDouble()
                        else couponResponse.gst.normal.totalSgst.toDouble()
                    cgst =
                        if (walletChecked) couponResponse.gstWithWallet.normal.totalCgst.toDouble()
                        else couponResponse.gst.normal.totalCgst.toDouble()
                    packagingFee = couponResponse.total_packing_price.toDouble()
                } else {
                    val couponResponse = state.applyCouponResponse!!
                    deliveryCharge = couponResponse.shipping.expressShipping.toDouble()

                    val tPrice =
                        if (walletChecked) couponResponse.new_final_price.withWallet!!.express
                        else couponResponse.new_final_price.express
                    totalPrice = decimalFormat.format(tPrice.toDouble()).toDouble()

                    igst =
                        if (walletChecked) couponResponse.gstWithWallet.express.totalIgst.toDouble()
                        else couponResponse.gst.express.totalIgst.toDouble()
                    sgst =
                        if (walletChecked) couponResponse.gstWithWallet.express.totalSgst.toDouble()
                        else couponResponse.gst.express.totalSgst.toDouble()
                    cgst =
                        if (walletChecked) couponResponse.gstWithWallet.express.totalCgst.toDouble()
                        else couponResponse.gst.express.totalCgst.toDouble()
                    packagingFee = couponResponse.total_packing_price.toDouble()
                }
            }

            state.cart != null -> {
                if (state.deliveryType == DeliveryType.Standard) {
                    val cartData = state.cart!!
                    deliveryCharge = cartData.shipping.expressShipping.toDouble()

                    val tPrice = if (walletChecked) cartData.newFinalPrice.withWallet!!.express
                    else cartData.newFinalPrice.express
                    totalPrice = decimalFormat.format(tPrice.toDouble()).toDouble()

                    igst = if (walletChecked) cartData.gstWithPoint.express.totalIgst.toDouble()
                    else cartData.gst.express.totalIgst.toDouble()
                    sgst = if (walletChecked) cartData.gstWithPoint.express.totalSgst.toDouble()
                    else cartData.gst.express.totalSgst.toDouble()
                    cgst = if (walletChecked) cartData.gstWithPoint.express.totalCgst.toDouble()
                    else cartData.gst.express.totalCgst.toDouble()
                    packagingFee = cartData.totalPackingPrice.toDouble()
                } else {

                }
            }

            else -> {
                state = state.copy(finish = false)
            }

        }

        if (state.deliveryType == DeliveryType.Express)
            showDialogExpress = true
    }

    private fun setPriceAfterWalletStatusChange() {
        if (checkWalletDiscountValidation()) {
            if (walletChecked) {
                if (state.applyCouponResponse != null) {
                    val couponResponse = state.applyCouponResponse
                    val tPrice =
                        if (state.deliveryType == DeliveryType.Express) couponResponse!!.new_final_price.withWallet!!.express.toDouble()
                        else couponResponse!!.new_final_price.withWallet!!.normal.toDouble()
                    totalPrice = decimalFormat.format(tPrice).toDouble()
                    igst =
                        if (state.deliveryType == DeliveryType.Express) couponResponse.gst.express.totalIgst.toDouble()
                        else couponResponse.gst.normal.totalIgst.toDouble()
                    cgst =
                        if (state.deliveryType == DeliveryType.Express) couponResponse.gst.express.totalCgst.toDouble()
                        else couponResponse.gst.normal.totalCgst.toDouble()
                    sgst =
                        if (state.deliveryType == DeliveryType.Express) couponResponse.gst.express.totalSgst.toDouble()
                        else couponResponse.gst.normal.totalSgst.toDouble()
                } else {
                    val cartItemResponse = state.cart
                    val tPrice =
                        if (state.deliveryType == DeliveryType.Express) cartItemResponse!!.newFinalPrice.withWallet!!.express.toDouble()
                        else cartItemResponse!!.newFinalPrice.withWallet!!.normal.toDouble()
                    totalPrice = decimalFormat.format(tPrice).toDouble()
                    igst =
                        if (state.deliveryType == DeliveryType.Express) cartItemResponse.gstWithPoint.express.totalIgst.toDouble()
                        else cartItemResponse.gstWithPoint.normal.totalIgst.toDouble()
                    cgst =
                        if (state.deliveryType == DeliveryType.Express) cartItemResponse.gstWithPoint.express.totalCgst.toDouble()
                        else cartItemResponse.gstWithPoint.normal.totalCgst.toDouble()
                    sgst =
                        if (state.deliveryType == DeliveryType.Express) cartItemResponse.gstWithPoint.express.totalSgst.toDouble()
                        else cartItemResponse.gstWithPoint.normal.totalSgst.toDouble()
                }
            } else {
                val couponResponse = state.applyCouponResponse
                if (couponResponse != null) {
                    if (state.applyCouponResponse != null) {
                        val couponResponse = state.applyCouponResponse
                        val tPrice =
                            if (state.deliveryType == DeliveryType.Express) couponResponse!!.new_final_price.express.toDouble()
                            else couponResponse!!.new_final_price.normal.toDouble()
                        totalPrice = decimalFormat.format(tPrice).toDouble()
                        igst =
                            if (state.deliveryType == DeliveryType.Express) couponResponse.gst.express.totalIgst.toDouble()
                            else couponResponse.gst.normal.totalIgst.toDouble()
                        cgst =
                            if (state.deliveryType == DeliveryType.Express) couponResponse.gst.express.totalCgst.toDouble()
                            else couponResponse.gst.normal.totalCgst.toDouble()
                        sgst =
                            if (state.deliveryType == DeliveryType.Express) couponResponse.gst.express.totalSgst.toDouble()
                            else couponResponse.gst.normal.totalSgst.toDouble()
                    } else {
                        val cartItemResponse = state.cart
                        val tPrice =
                            if (state.deliveryType == DeliveryType.Express) cartItemResponse!!.newFinalPrice.express.toDouble()
                            else cartItemResponse!!.newFinalPrice.normal.toDouble()
                        totalPrice = decimalFormat.format(tPrice).toDouble()
                        igst =
                            if (state.deliveryType == DeliveryType.Express) cartItemResponse.gst.express.totalIgst.toDouble()
                            else cartItemResponse.gst.normal.totalIgst.toDouble()
                        cgst =
                            if (state.deliveryType == DeliveryType.Express) cartItemResponse.gst.express.totalCgst.toDouble()
                            else cartItemResponse.gst.normal.totalCgst.toDouble()
                        sgst =
                            if (state.deliveryType == DeliveryType.Express) cartItemResponse.gst.express.totalSgst.toDouble()
                            else cartItemResponse.gst.normal.totalSgst.toDouble()
                    }
                }
            }
        } else {
            walletChecked = false
            walletDiscountInfo()
        }
    }

    private fun checkWalletDiscountValidation(): Boolean {
        val priceProduct = state.cart!!.cartprice
        return !(priceProduct.toDouble() < 990 || priceProduct.toDouble() > 15000)
    }

    private fun walletDiscountInfo() {
        viewModelScope.launch {
            myPlanUseCase.execute().collect { result ->
                when (result) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        val data = result.data
                        state = state.copy(
                            isLoading = false,
                            myPlan = data
                        )
                        if (data != null) {
                            val pointSettings: MyPlanModel.PointSettings = data.pointSettings
                            minOrder = pointSettings.pointRedeemMinimumOrderValue
                            maxOrder = pointSettings.pointRedeemMaximumOrderValue
                            walletPoint = pointSettings.maxRedeemPointPerOrder

                        }
                    }

                    is Resource.Error ->
                        state = state.copy(
                            isLoading = false,
                            isError = true,
                            errorMessage = result.message
                        )
                }

            }
        }
    }

    private fun getCart(
        isLoading: Boolean = true
    ) {
        viewModelScope.launch {
            cartUseCase.execute().collect { result ->
                when (result) {
                    is Resource.Loading -> state = state.copy(isLoading = isLoading)
                    is Resource.Success -> {
                        val data = result.data
                        T2PApp.cartCount = if (data!!.result.isEmpty()) 0 else data.result.size
                        state = state.copy(
                            isLoading = false,
                            cart = data
                        )
                        if (state.defaultAddress != null)
                            setPrice()
                    }

                    is Resource.Error ->
                        state = state.copy(
                            isLoading = false,
                            isError = true,
                            errorMessage = result.message
                        )
                }

            }
        }
    }

    private fun setPrice() {
        if (state.cart != null && state.cart!!.result.isNotEmpty())
            state.cart!!.apply {
                price = cartprice.toDouble()
                deliveryCharge =
                    if (state.deliveryType == DeliveryType.Standard) shipping.normalShipping.toDouble()
                    else shipping.expressShipping.toDouble()
                packagingFee = totalPackingPrice.toDouble()
                cgst =
                    if (state.deliveryType == DeliveryType.Standard) gst.normal.totalCgst.toDouble()
                    else gst.express.totalCgst.toDouble()
                igst =
                    if (state.deliveryType == DeliveryType.Standard) gst.normal.totalIgst.toDouble()
                    else gst.express.totalIgst.toDouble()
                sgst =
                    if (state.deliveryType == DeliveryType.Standard) gst.normal.totalSgst.toDouble()
                    else gst.express.totalSgst.toDouble()
                couponDiscount = 0.0
                subscriptionDiscount = planDiscount.toDouble()

                //wallet
                walletEnabled = newFinalPrice.withWallet != null
                if (walletEnabled) {
                    discountWallet = walletDiscount.toDouble()
                    pointConversionText = "($customerPoint x $onePointValue)"
                }

                //cod enabled
                codEnabled = openOrderValue < maxOpenCodOrder

                if (state.defaultAddress != null)
                    checkCutOffTime(
                        result.first().city,
                        state.defaultAddress!!.city.id
                    )

                //total
                totalPrice = decimalFormat.format(newFinalPrice.normal.toDouble()).toDouble()
            }
    }

    private fun checkCutOffTime(
        startCity: String,
        endCity: String
    ) {
        Log.e("cities", "Start city : $startCity\nEnd city : $endCity")
        viewModelScope.launch {
            cutOffTimeCheckUseCase.execute(
                startCity, endCity
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> state = state.copy(isLoading = false)
                    is Resource.Success -> {
                        val data = result.data
                        val isError = data?.status == Status.error.name

                        Log.e("cities", "Data is : $data")

                        state = state.copy(
                            isLoading = false,
                            isError = isError,
                            cutOffTimeCheckModel = data,
                            finish = !isError
                        )
                        setDataAfterCheckCutOffTime()
                    }

                    is Resource.Error ->
                        state = state.copy(
                            isLoading = false,
                            isError = true,
                            errorMessage = result.message,
                            finish = false
                        )
                }

            }
        }
    }

    private fun setDataAfterCheckCutOffTime() {
        if (state.cutOffTimeCheckModel != null)
            state.cutOffTimeCheckModel!!.result.apply {
                expressEnabled = express

                val timeSlots = mutableListOf<String>()
                if (timeslot != null) {
                    state = state.copy(timeSlots = emptyList())
                    if (timeslot.contentEquals("Both")) {
                        timeSlots.add("Night")
                        timeSlots.add("Afternoon")
                    } else {
                        timeSlots.add(timeslot)
                    }
                    state = state.copy(timeSlots = timeSlots)
                } else {
                    state = state.copy(
                        isError = true,
                        errorMessage = "No available slots!, retry later",
                        finish = false
                    )
                }
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
        setPrice()
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

    private fun getSettings() {
        viewModelScope.launch {
            state = state.copy(settings = userPref.getSettings())
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
                            applyCouponResponse = if (!isError) data else null,
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