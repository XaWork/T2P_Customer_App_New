package me.taste2plate.app.customer.presentation.screens.cart

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facebook.appevents.AppEventsConstants
import com.facebook.appevents.AppEventsLogger
import com.razorpay.Checkout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.taste2plate.app.customer.MainActivity
import me.taste2plate.app.customer.T2PApp
import me.taste2plate.app.customer.data.Resource
import me.taste2plate.app.customer.data.Status
import me.taste2plate.app.customer.data.UserPref
import me.taste2plate.app.customer.domain.model.custom.LogRequest
import me.taste2plate.app.customer.domain.model.custom.LogType
import me.taste2plate.app.customer.domain.model.tracking.EventTraits
import me.taste2plate.app.customer.domain.model.tracking.TrackEventModel
import me.taste2plate.app.customer.domain.model.user.MyPlanModel
import me.taste2plate.app.customer.domain.model.user.address.AddressListModel
import me.taste2plate.app.customer.domain.use_case.ApplyCouponUseCase
import me.taste2plate.app.customer.domain.use_case.CouponByCityUseCase
import me.taste2plate.app.customer.domain.use_case.analytics.AddLogUseCase
import me.taste2plate.app.customer.domain.use_case.interakt.TrackEventUseCase
import me.taste2plate.app.customer.domain.use_case.product.CalculateCheckoutDistanceUseCase
import me.taste2plate.app.customer.domain.use_case.product.CutOffTimeCheckUseCase
import me.taste2plate.app.customer.domain.use_case.user.InitCheckoutUseCase
import me.taste2plate.app.customer.domain.use_case.user.MyPlanUseCase
import me.taste2plate.app.customer.domain.use_case.user.OrderConfirmUseCase
import me.taste2plate.app.customer.domain.use_case.user.address.AllAddressUseCase
import me.taste2plate.app.customer.domain.use_case.user.cart.CartUseCase
import me.taste2plate.app.customer.domain.use_case.user.cart.DeleteCartUseCase
import me.taste2plate.app.customer.domain.use_case.user.cart.UpdateCartUseCase
import me.taste2plate.app.customer.presentation.screens.checkout.DeliveryType
import me.taste2plate.app.customer.presentation.screens.checkout.PaymentType
import me.taste2plate.app.customer.utils.toDecimal
import org.json.JSONObject
import java.util.Currency
import javax.inject.Inject

@HiltViewModel
class CheckOutViewModel @Inject constructor(
    private val userPref: UserPref,
    private val addLogUseCase: AddLogUseCase,
    private val cartUseCase: CartUseCase,
    private val updateCartUseCase: UpdateCartUseCase,
    private val calculateCheckoutDistanceUseCase: CalculateCheckoutDistanceUseCase,
    private val deleteCartUseCase: DeleteCartUseCase,
    private val allAddressUseCase: AllAddressUseCase,
    private val cutOffTimeCheckUseCase: CutOffTimeCheckUseCase,
    private val couponByCityUseCase: CouponByCityUseCase,
    private val applyCouponUseCase: ApplyCouponUseCase,
    private val myPlanUseCase: MyPlanUseCase,
    private val trackEventUseCase: TrackEventUseCase,
    private val initCheckoutUseCase: InitCheckoutUseCase,
    private val orderConfirmUseCase: OrderConfirmUseCase,
) : ViewModel() {

    var state by mutableStateOf(CheckoutState())

    var walletChecked by mutableStateOf(false)
    var walletEnabled by mutableStateOf(false)
    var showDialogExpress by mutableStateOf(false)
    var showCustomDialog by mutableStateOf(false)
    var validPaymentType by mutableStateOf(true)
    var showDigitalCODDialog by mutableStateOf(false)
    var customDialogMessage by mutableStateOf("")
    var codEnabled by mutableStateOf(true)
    var expressEnabled by mutableStateOf(true)

    var selectedDate by mutableStateOf("")
    var customTip by mutableStateOf("")

    var appliedCoupon by mutableStateOf("")
    var selectedTimeSlot by mutableStateOf("")
    var pointConversionText by mutableStateOf("")
    var price by mutableDoubleStateOf(0.0)
    var deliveryCharge by mutableDoubleStateOf(0.0)
    var packagingFee by mutableDoubleStateOf(0.0)
    var cgst by mutableDoubleStateOf(0.0)
    var youSave by mutableDoubleStateOf(0.0)
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
        // player.prepare()
        getCart()
        getSettings()
        getDefaultAddress(applyCouponAndSetPrice = false)
    }

    override fun onCleared() {
        super.onCleared()
        // player.release()
    }

    fun onEvent(event: CheckoutEvents) {
        when (event) {
            is CheckoutEvents.GetCart -> {}
            is CheckoutEvents.RemoveCoupon -> {
                if (state.deliveryType == DeliveryType.Standard)
                    codEnabled = true
                state = state.copy(applyCouponResponse = null)
                getCart()
            }

            is CheckoutEvents.Checkout -> {
                allDetailFilled(event.context)
            }

            is CheckoutEvents.AddLog -> {
                addLog(event.logRequest)
            }

            is CheckoutEvents.ChangeMyPlanValue -> {
                state = state.copy(myPlan = null)
            }

            is CheckoutEvents.CalculateCheckoutDistance -> {
                calculateCheckoutDistance()
            }

            is CheckoutEvents.ChangeWalletCheckStatus -> {
                walletChecked = !walletChecked
                setPriceAfterWalletStatusChange()
            }

            is CheckoutEvents.ApplyCoupon -> {
                applyCoupon(event.couponCode)
            }

            is CheckoutEvents.ChangeDeliveryType -> {
                state = state.copy(
                    deliveryType = event.deliveryType,
                    paymentType = PaymentType.Online
                )
                setPriceAfterChangeDeliveryType()
            }

            is CheckoutEvents.ChangePaymentType -> {
                state = state.copy(paymentType = event.paymentType)
                validPaymentType = true
                checkPriceRequirements()
            }

            is CheckoutEvents.GetUser -> {}

            is CheckoutEvents.UpdateTip -> {
                val index = event.index

                val updatedTipList = state.tips.mapIndexed { i, tip ->
                    if (i == index) {
                        tip.copy(
                            selected = !tip.selected, tipPrice = if (state.tips[index].other)
                                event.otherTipPrice else state.tips[index].tipPrice
                        )
                    } else {
                        tip.copy(selected = false)
                    }
                }

                state = state.copy(tips = updatedTipList)

                val tipPrice = if (index != 3) state.tips[index].tipPrice.toDouble()
                else customTip.toDouble()
                Log.e("Tip", "Tip price $tipPrice\nSelected : ${state.tips[index].selected}")
                if (state.tips[index].selected) {
                    totalPrice += tipPrice
                } else {
                    totalPrice -= tipPrice
                }
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
                        event.quantity,
                        event.context
                    )
                else
                    deleteCart(event.productId)
            }
        }
    }

    private fun playVideo(uri: Uri) {
        //player.setMediaItem(MediaItem.fromUri(uri))
    }

    private fun addLog(logRequest: LogRequest) {
        viewModelScope.launch {
            addLogUseCase.execute(logRequest)
        }
    }

    private fun addInteraKtLog(trackData: TrackEventModel) {
        viewModelScope.launch {
            trackEventUseCase.execute(
                trackData
            )
        }
    }

    private fun allDetailFilled(context: Context) {
        val priceToCheck = if (state.deliveryType == DeliveryType.Express) {
            state.cart!!.newFinalPrice.express.toFloat()
        } else {
            state.cart!!.newFinalPrice.normal.toFloat()
        }
        val minPrice =
            state.settings!!.minimumOrderValue.toFloat()

        if (selectedDate.isEmpty() && selectedTimeSlot.isEmpty())
            state = state.copy(isError = true, errorMessage = "Select data and time.")
        else if (priceToCheck < minPrice) {
            customDialogMessage = "Order can be placed for amount bigger than Rs. $minPrice"
            showCustomDialog = true
        } else {
            checkPriceRequirements(showCodDigitalDialog = false)
            if (validPaymentType)
                initCheckout(context)
        }
    }

    private fun checkPriceRequirements(
        showCodDigitalDialog: Boolean = true,
    ) {
        val priceToCheck = if (state.deliveryType == DeliveryType.Express) {
            state.cart!!.cartprice.toFloat()
            // state.cart!!.newFinalPrice.express.toFloat()
        } else {
            state.cart!!.cartprice.toFloat()
            //state.cart!!.newFinalPrice.normal.toFloat()
        }
        val minPrice = state.settings!!.minimumOrderValue.toFloat()
        val maxPrice = state.settings!!.maximumOrderValueCod.toFloat()

        when (state.paymentType) {
            PaymentType.Online -> {
                if (priceToCheck < minPrice) {
                    showCustomDialog = true
                    validPaymentType = false
                    customDialogMessage =
                        "Online payment order can be placed for minimum amount of Rs. $minPrice"
                }
            }

            PaymentType.COD -> {
                if (priceToCheck !in minPrice..maxPrice) {
                    validPaymentType = false
                    customDialogMessage =
                        "COD order can be placed for amount between Rs. $minPrice - Rs. $maxPrice"
                    showCustomDialog = true
                } else {
                    showDigitalCODDialog = showCodDigitalDialog
                    validPaymentType = true
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
                    totalPrice = tPrice.toDecimal()

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
                    totalPrice = tPrice.toDecimal()

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
                    codEnabled = false
                    val cartData = state.cart!!
                    deliveryCharge = cartData.shipping.normalShipping.toDouble()

                    val tPrice = if (walletChecked) cartData.newFinalPrice.withWallet!!.normal
                    else cartData.newFinalPrice.normal
                    totalPrice = tPrice.toDecimal()

                    igst = if (walletChecked) cartData.gstWithPoint.normal.totalIgst.toDouble()
                    else cartData.gst.normal.totalIgst.toDouble()
                    sgst = if (walletChecked) cartData.gstWithPoint.normal.totalSgst.toDouble()
                    else cartData.gst.normal.totalSgst.toDouble()
                    cgst = if (walletChecked) cartData.gstWithPoint.normal.totalCgst.toDouble()
                    else cartData.gst.normal.totalCgst.toDouble()
                    packagingFee = cartData.totalPackingPrice.toDouble()
                } else {
                    val cartData = state.cart!!
                    deliveryCharge = cartData.shipping.expressShipping.toDouble()

                    val tPrice = if (walletChecked) cartData.newFinalPrice.withWallet!!.express
                    else cartData.newFinalPrice.express
                    totalPrice = tPrice.toDecimal()

                    igst = if (walletChecked) cartData.gstWithPoint.express.totalIgst.toDouble()
                    else cartData.gst.express.totalIgst.toDouble()
                    sgst = if (walletChecked) cartData.gstWithPoint.express.totalSgst.toDouble()
                    else cartData.gst.express.totalSgst.toDouble()
                    cgst = if (walletChecked) cartData.gstWithPoint.express.totalCgst.toDouble()
                    else cartData.gst.express.totalCgst.toDouble()
                    packagingFee = cartData.totalPackingPrice.toDouble()
                }
            }

            else -> {
                state = state.copy(finish = false)
            }

        }
        if (state.deliveryType == DeliveryType.Express) {
            codEnabled = false
            showDialogExpress = true
        } else codEnabled = state.applyCouponResponse == null
    }

    private fun setPriceAfterApplyCoupon() {
        state.applyCouponResponse!!.run {
            price = cartprice.toDouble()
            packagingFee = total_packing_price.toDouble()
            couponDiscount = coupon_discount.toString().toDecimal()

            when (state.deliveryType) {
                DeliveryType.Express -> {
                    totalPrice = new_final_price.express.toDecimal()
                    igst = gst.express.totalIgst.toDecimal()
                    cgst = gst.express.totalCgst.toDecimal()
                    sgst = gst.express.totalSgst.toDecimal()
                }

                DeliveryType.Standard -> {
                    totalPrice = new_final_price.normal.toDecimal()
                    igst = gst.normal.totalIgst.toDecimal()
                    cgst = gst.normal.totalCgst.toDecimal()
                    sgst = gst.normal.totalSgst.toDecimal()
                }
            }
        }

        val totPrice = totalPrice + state.cart?.lastMileLongDistanceExtraCharge!!.toDouble()
        Log.e(
            "pricetocheck",
            "Total price : ${totalPrice}${state.cart?.lastMileLongDistanceExtraCharge?.toDecimal()}\n" +
                    "${totalPrice + state.cart?.lastMileLongDistanceExtraCharge!!.toDouble()}\n" +
                    "${totPrice.toString().toDecimal()}"
        )

        startTimer()
    }

    private fun setPriceAfterWalletStatusChange() {
        if (checkWalletDiscountValidation()) {
            if (walletChecked) {
                youSave += discountWallet
                if (state.applyCouponResponse != null) {
                    val couponResponse = state.applyCouponResponse
                    val tPrice =
                        if (state.deliveryType == DeliveryType.Express) couponResponse!!.new_final_price.withWallet!!.express
                        else couponResponse!!.new_final_price.withWallet!!.normal
                    totalPrice = tPrice.toDecimal()
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
                        if (state.deliveryType == DeliveryType.Express) cartItemResponse!!.newFinalPrice.withWallet!!.express
                        else cartItemResponse!!.newFinalPrice.withWallet!!.normal
                    totalPrice = tPrice.toDecimal()
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
                youSave -= discountWallet
                if (state.applyCouponResponse != null) {
                    val couponResponse = state.applyCouponResponse
                    val tPrice =
                        if (state.deliveryType == DeliveryType.Express) couponResponse!!.new_final_price.express
                        else couponResponse!!.new_final_price.normal
                    totalPrice = tPrice.toDecimal()
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
                        if (state.deliveryType == DeliveryType.Express) cartItemResponse!!.newFinalPrice.express
                        else cartItemResponse!!.newFinalPrice.normal
                    totalPrice = tPrice.toDecimal()
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
        } else {
            walletChecked = false
            walletDiscountInfo()
        }
    }

    private fun checkWalletDiscountValidation(): Boolean {
        val priceProduct = state.cart!!.cartprice
        return !(priceProduct.toDouble() < 900 || priceProduct.toDouble() > 15000)
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
                        val isError = data!!.status == Status.error.name

                        T2PApp.cartCount =
                            if (isError || data.result.isEmpty()) 0 else data.result.size

                        state = state.copy(
                            isLoading = false,
                            cart = data
                        )
                        if (state.defaultAddress != null) {
                            setPrice()
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

    private fun confirmOrder(
        gateWay: String,
        orderId: String,
        transactionId: String,
        context: Context
    ) {

        viewModelScope.launch {
            orderConfirmUseCase.execute(
                isWalletApplied = walletChecked,
                gateWay, orderId, transactionId
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> state = state.copy(buttonLoading = true)
                    is Resource.Success -> {
                        val isError = result.data!!.status == Status.error.name
                        val data = result.data

                        state = state.copy(
                            buttonLoading = false,
                            orderConfirmModel = data,
                            isError = isError,
                            errorMessage = if (isError) data.message else null
                        )



                        if (!isError) {
                            logPurchasedEvent(
                                state.cart!!.result[0].quantity, state.cart!!.result[0].product.id,
                                state.cart!!.finalPrice.toDouble(), context
                            )

                            addLog(
                                LogRequest(
                                    type = LogType.checkout,
                                    event = "order confirmed",
                                    page_name = "/checkout",
                                    order_id = orderId
                                )
                            )

                            //interakt
                            addInteraKtLog(
                                TrackEventModel(
                                    event = LogType.addToWishlist,
                                    traits = EventTraits(
                                        orderId = orderId
                                    )
                                )
                            )

                            state = state.copy(
                                isError = true,
                                errorMessage = "Order placed",
                                showOrderConfirmGif = true
                            )

                            startTimer(orderConfirmed = true)
                        }
                    }

                    is Resource.Error ->
                        state = state.copy(
                            buttonLoading = false,
                            isError = true,
                            errorMessage = result.message
                        )
                }

            }
        }
    }

    private fun logPurchasedEvent(
        numItems: Int,
        contentId: String?,
        price: Double,
        context: Context
    ) {
        Log.e(
            "facebook",
            "Facebook log purchase event \nnumItems: $numItems, contentId: $contentId, price: $price"
        )
        val logger = AppEventsLogger.newLogger(context)
        val params = Bundle()
        params.putInt(AppEventsConstants.EVENT_PARAM_NUM_ITEMS, numItems)
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_TYPE, "product")
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_ID, contentId)
        params.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, "INR")
        logger.logPurchase(price.toBigDecimal(), Currency.getInstance("INR"), params)
    }


    private fun setPrice() {
        if (state.cart != null && state.cart!!.result.isNotEmpty())
            state.cart!!.apply {
                youSave = 0.0
                this.result.forEach {
                    val sellingPrice = it.product.sellingPrice
                    if (!sellingPrice.isNullOrEmpty()) {
                        youSave += it.product.price.toFloat() - sellingPrice.toFloat()
                    }
                }
                youSave *= result[0].quantity

                //by doing this no crash is found otherwise sometime cart price is null
                price = "${cartprice ?: "0"}".toDouble()
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
                // codEnabled = openOrderValue < maxOpenCodOrder

                if (state.defaultAddress != null)
                    checkCutOffTime(
                        result.first().city,
                        state.defaultAddress!!.city.id
                    )

                //total
                totalPrice = newFinalPrice.normal.toDecimal()
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

    private fun initCheckout(
        context: Context
    ) {
        var deliveryCost = ""
        if (state.deliveryType == DeliveryType.Express) {
            deliveryCost = if (state.applyCouponResponse != null)
                state.applyCouponResponse!!.shipping.expressShipping
            else
                state.cart!!.shipping.expressShipping
        }

        var tipPrice = "0"
        state.tips.forEach { if (it.selected) tipPrice = it.tipPrice.toString() }

        var finalPrice = ""
        if (state.deliveryType == DeliveryType.Express) {
            finalPrice = if (state.applyCouponResponse != null) {
                if (walletChecked) {
                    state.applyCouponResponse!!.new_final_price.withWallet!!.express
                } else {
                    state.applyCouponResponse!!.new_final_price.express
                }
            } else {
                if (walletChecked) {
                    state.cart!!.newFinalPrice.withWallet!!.express
                } else {
                    state.cart!!.newFinalPrice.express
                }
            }
        } else {
            finalPrice = if (state.applyCouponResponse != null) {
                if (walletChecked) {
                    state.applyCouponResponse!!.new_final_price.withWallet!!.normal
                } else {
                    state.applyCouponResponse!!.new_final_price.normal
                }
            } else {
                if (walletChecked) {
                    state.cart!!.newFinalPrice.withWallet!!.normal
                } else {
                    state.cart!!.newFinalPrice.normal
                }
            }
        }

        viewModelScope.launch {
            initCheckoutUseCase.execute(
                walletDiscount = walletChecked,
                timeSlot = selectedTimeSlot,
                date = selectedDate,
                deliveryCost = deliveryCost,
                express = if (state.deliveryType == DeliveryType.Express) "Y" else "N",
                couponCode = appliedCoupon,
                couponType = if (state.applyCouponResponse != null) state.applyCouponResponse!!.coupon_type else "",
                couponAmount = if (state.applyCouponResponse != null) state.applyCouponResponse!!.coupon_discount.toString() else "",
                cartPrice = state.cart!!.cartprice,
                tipPrice = tipPrice,
                finalPrice = finalPrice,
                addCost = "Of",
                browser = "Android"
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> state = state.copy(buttonLoading = true)
                    is Resource.Success -> {
                        val data = result.data
                        val isError = data?.status == Status.error.name

                        state = state.copy(
                            buttonLoading = false,
                            isError = isError,
                            errorMessage = if (isError) data!!.message else null,
                            checkoutModel = data,
                        )

                        if (!isError) {
                            state = state.copy(
                                isError = true,
                                errorMessage = "Order Created in Pending Status!"
                            )

                            if (state.paymentType == PaymentType.Online) {
                                // Log.e("payment", "$finalPrice is this")
                                startPayment(context, finalPrice.toDouble())
                            } else {
                                confirmOrder(
                                    gateWay = "COD",
                                    orderId = state.checkoutModel!!.orderId,
                                    transactionId = "",
                                    context = context
                                )
                            }
                        }
                    }

                    is Resource.Error ->
                        state = state.copy(
                            buttonLoading = false,
                            isError = true,
                            errorMessage = result.message,
                            finish = false
                        )
                }

            }
        }
    }

    private fun startPayment(context: Context, price: Double) {
        val activity: MainActivity = context as MainActivity
        Checkout.preload(T2PApp.applicationContext())
        val co = Checkout()
        co.setKeyID("rzp_live_ZLgzjgdHBJDlP8")

        try {
            val options = JSONObject();
            options.put("name", "Taste2Plate")
            options.put("description", "Taste2Plate Order")
            options.put("currency", "INR")
            options.put("amount", (price * 100).toFloat())

            val preFill = JSONObject();
            val address = state.defaultAddress!!
            val user = state.user!!
            preFill.put("email", user.email)
            preFill.put("contact", address.contactMobile)
            options.put("prefill", preFill)
            co.open(activity, options)

            activity.setRazorpayPaymentSuccess(object : MainActivity.RazorpayPaymentSuccess {
                override fun onPaymentSuccess(p0: String?) {
                    Log.e("Payment", "Payment success in viewModel $p0")
                    confirmOrder(
                        gateWay = "Online",
                        orderId = state.checkoutModel!!.orderId,
                        transactionId = p0!!,
                        context
                    )
                }
            })
        } catch (e: Exception) {
            state = state.copy(isError = true, errorMessage = "Error in payment: " + e.message)
            Log.e("Payment", "Error in payment")
            e.printStackTrace();
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
        quantity: Int,
        context: Context
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

                        if (!isError) {
                            addAppEvent(context, productId, quantity)
                            addLog(
                                LogRequest(
                                    type = LogType.addToCart,
                                    event = "update Cart",
                                    page_name = "/cart",
                                    product_id = productId
                                )
                            )
                            getCart(isLoading = false)
                        }

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

    private fun addAppEvent(context: Context, productId: String, quantity: Int) {
        //facebbook
        val logger = AppEventsLogger.newLogger(context)
        val params = Bundle()

        params.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, "INR");
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_TYPE, "product");
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_ID, productId);
        params.putString(AppEventsConstants.EVENT_PARAM_NUM_ITEMS, quantity.toString());

        logger.logEvent(
            AppEventsConstants.EVENT_NAME_ADDED_TO_CART,
            params
        )
    }

    private fun calculateCheckoutDistance() {
        viewModelScope.launch {
            calculateCheckoutDistanceUseCase.execute(
                state.cart!!.result[0].product.id
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
                            calculateCheckoutDistanceModel = data,
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

                        if (!isError) {
                            getCart(isLoading = false)
                            addLog(
                                LogRequest(
                                    type = LogType.actionPerform,
                                    event = "delete Cart",
                                    page_name = "/cart",
                                    product_id = productId
                                )
                            )
                        }

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

    private fun getDefaultAddress(applyCouponAndSetPrice: Boolean = true) {
        viewModelScope.launch {
            state = state.copy(defaultAddress = userPref.getDefaultAddress())
            getUser()
        }
        if (applyCouponAndSetPrice) {
            setPrice()
            getCoupons()
        }
    }

    private fun setDefaultAddress(address: AddressListModel.Result) {
        viewModelScope.launch {
            userPref.saveDefaultAddress(address)
            state = state.copy(defaultAddress = address)
            getCart(isLoading = false)
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

                        /*if (data != null && data.coupon.isNotEmpty())
                            applyCoupon(data.coupon[0].coupon)*/

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
                when (result) {
                    is Resource.Loading -> state = state.copy(isLoading = false)
                    is Resource.Success -> {
                        val data = result.data
                        val isError = data?.status == Status.error.name

                        state = state.copy(
                            isLoading = false,
                            isError = isError,
                            errorMessage = if (isError) data?.message else null,
                            applyCouponResponse = if (!isError) data else null,
                            showCouponGif = !isError,
                        )

                        //COD is not applicable after coupon applied
                        if (!isError) {
                            appliedCoupon = couponCode
                            codEnabled = false
                            state = state.copy(paymentType = PaymentType.Online)
                            setPriceAfterApplyCoupon()
                            addLog(
                                LogRequest(
                                    type = LogType.actionPerform,
                                    event = "coupon applied",
                                    page_name = "/checkout",
                                    event_data = couponCode
                                )
                            )

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

    private fun startTimer(orderConfirmed: Boolean = false) {
        viewModelScope.launch {
            /*  while (state.showGifTime == 0) {
                  delay(1000)
                  state = state.copy(
                      showGifTime = state.showGifTime--,
                      orderConfirmed = orderConfirmed && state.showGifTime == 0
                  )
              }*/

            delay(3500)
            state = state.copy(
                showGifTime = 4,
                showCouponGif = false,
                showOrderConfirmGif = false,
                orderConfirmed = orderConfirmed
            )
        }

    }
}