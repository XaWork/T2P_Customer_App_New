package me.taste2plate.app.customer.presentation.screens.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facebook.appevents.AppEventsConstants
import com.facebook.appevents.AppEventsLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.taste2plate.app.customer.T2PApp
import me.taste2plate.app.customer.data.Resource
import me.taste2plate.app.customer.data.Status
import me.taste2plate.app.customer.data.Taste
import me.taste2plate.app.customer.data.UserPref
import me.taste2plate.app.customer.domain.model.SettingsModel
import me.taste2plate.app.customer.domain.model.custom.LogRequest
import me.taste2plate.app.customer.domain.model.custom.LogType
import me.taste2plate.app.customer.domain.model.user.address.AddressListModel
import me.taste2plate.app.customer.domain.use_case.HomeUseCase
import me.taste2plate.app.customer.domain.use_case.SettingsUseCase
import me.taste2plate.app.customer.domain.use_case.analytics.AddLogUseCase
import me.taste2plate.app.customer.domain.use_case.user.address.AllAddressUseCase
import me.taste2plate.app.customer.domain.use_case.user.cart.AddToCartUseCase
import me.taste2plate.app.customer.domain.use_case.user.cart.CartUseCase
import me.taste2plate.app.customer.domain.use_case.user.cart.DeleteCartUseCase
import me.taste2plate.app.customer.domain.use_case.user.cart.UpdateCartUseCase
import me.taste2plate.app.customer.domain.use_case.user.wishlist.AddToWishlistUseCase
import me.taste2plate.app.customer.domain.use_case.user.wishlist.RemoveFromWishlistUseCase
import me.taste2plate.app.customer.domain.use_case.user.wishlist.WishlistUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val userPref: UserPref,
    private val homeUseCase: HomeUseCase,
    private val wishlistUseCase: WishlistUseCase,
    private val cartUseCase: CartUseCase,
    private val addToWishlistUseCase: AddToWishlistUseCase,
    private val removeFromWishlistUseCase: RemoveFromWishlistUseCase,
    private val allAddressUseCase: AllAddressUseCase,
    private val updateCartUseCase: UpdateCartUseCase,
    private val deleteCartUseCase: DeleteCartUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val addLogUseCase: AddLogUseCase,
    private val settingUseCase: SettingsUseCase
) : ViewModel() {

    var state by mutableStateOf(HomeState())

    init {
        getSettings()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.GetHome -> {
                getHomeData()
            }

            is HomeEvent.GetWishlist -> {
                getWishlist()
            }

            is HomeEvent.AddLog -> {
                addLog(event.logRequest)
            }

            is HomeEvent.LogOut -> {
                viewModelScope.launch {
                    userPref.logOut()
                }
            }

            is HomeEvent.ChangeTaste -> {
                setTaste()
            }

            is HomeEvent.GetAddress -> {
                getAddress()
            }

            is HomeEvent.AddToWishlist -> {
                val productId = event.productId
                val alreadyWishListed =
                    if (state.wishListData!!.result.isEmpty()) false else state.wishListData!!.result.any { it.product.id == productId }
                if (alreadyWishListed)
                    removeFromWishlist(productId)
                else
                    addToWishlist(event.productId)
            }

            is HomeEvent.UpdateCart -> {
                Log.e("UpdateCart", "Product id ${event.productId} \n Quantity: ${event.quantity}")
                when {
                    event.quantity == 0 -> {
                        deleteCart(event.productId)
                    }

                    state.cartData != null && state.cartData!!.result != null && state.cartData!!.result.isNotEmpty()
                    -> {
                        var itemInCart = false
                        state.cartData!!.result.forEach {
                            if (it.product.id == event.productId)
                                itemInCart = true
                        }

                        if (itemInCart) {
                            updateCart(
                                productId = event.productId,
                                quantity = event.quantity,
                                context = event.context
                            )
                        } else {
                            addToCart(event.context, event.productId)
                        }
                    }

                    else -> {
                        addToCart(event.context, event.productId)
                    }
                }
            }

            is HomeEvent.SetDefaultAddress -> {
                setDefaultAddress(event.address)
            }

            is HomeEvent.UpdateState -> {
                state = when {
                    event.changeAddToWishlistResponse -> {
                        state.copy(
                            message = null,
                            addToWishlistResponse = null,
                            addToCartResponse = null,
                            errorMessage = null,
                            isError = false,
                            showErrorMessage = false
                        )
                    }

                    else -> {
                        state.copy(
                            cartError = false
                        )
                    }
                }
            }

            HomeEvent.CheckDefaultAddress -> hasDefaultAddress()
        }
    }

    private fun addLog(logRequest: LogRequest) {
        viewModelScope.launch {
            addLogUseCase.execute(logRequest)
        }
    }

    private fun setTaste() {
        viewModelScope.launch {
            userPref.setTaste()
            getTaste()
            onEvent(HomeEvent.GetHome)
        }
    }

    private fun getTaste() {
        viewModelScope.launch {
            val taste = userPref.getTaste()
            val user = userPref.getUser()
            // val setting = userPref.getSettings()
            state = state.copy(checked = taste == Taste.nonVeg, user = user)
        }
    }

    private fun setDefaultAddress(
        address: AddressListModel.Result,
        isLoading: Boolean = false
    ) {
        viewModelScope.launch {
            userPref.saveDefaultAddress(address)
            state = state.copy(isLoading = isLoading, defaultAddress = address)
            getCart()
        }
    }

    private fun getSettings() {
        viewModelScope.launch {
            settingUseCase.execute().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        state = if (result.data?.status == Status.success.name) {
                            saveSetting(result.data)
                            state.copy(
                                setting = result.data.result
                            )
                        } else
                            state.copy(
                                isLoading = false,
                                isError = true,
                                errorMessage = "Something Went wrong"
                            )
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            isError = true,
                            errorMessage = result.message
                        )
                    }
                }
            }
        }
    }

    private fun saveSetting(setting: SettingsModel) {
        viewModelScope.launch {
            userPref.saveSettings(setting.result)
        }
        getTaste()
        getHomeData()
    }

    private fun getHomeData() {
        viewModelScope.launch {
            homeUseCase.execute(
                taste = userPref.getTaste()
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        state = if (result.data?.status == Status.success.name) {
                            getWishlist()
                            state.copy(
                                homeData = result.data
                            )
                        } else
                            state.copy(
                                isLoading = false,
                                isError = true,
                                errorMessage = "Something Went wrong"
                            )
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            isError = true,
                            errorMessage = result.message
                        )
                    }
                }
            }
        }
    }

    private fun hasDefaultAddress(): Boolean {
        var hasDefaultAddress = false

        viewModelScope.launch {
            val localAddress = userPref.getAddress()
            val defaultAddress = userPref.getDefaultAddress()
            if (localAddress != null || defaultAddress != null) {
                state = state.copy(
                    defaultAddress = defaultAddress,
                    localAddress = localAddress,
                    noAddressFound = false
                )
                Log.e(
                    "Address",
                    "default address is not null && Address not found is ${state.noAddressFound}"
                )
                hasDefaultAddress = true
            }
        }

        return hasDefaultAddress
    }

    private fun getWishlist() {
        viewModelScope.launch {
            wishlistUseCase.execute(
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        //state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        val isError = result.data?.status == Status.error.name
                        val data = result.data

                        state = state.copy(
                            wishListData = data,
                            isError = isError,
                            errorMessage = null,
                        )

                        //if (!isError)
                        T2PApp.wishlistCount = data?.result?.size ?: 0

                        if (hasDefaultAddress()) {
                            Log.e(
                                "Address",
                                "Address not found is ${state.noAddressFound}"
                            )
                            state = state.copy(noAddressFound = false)
                            getCart()
                        } else {
                            state = state.copy(noAddressFound = true)
                            Log.e(
                                "Address",
                                "Address not found is in else case${state.noAddressFound}"
                            )
                        }
                            //getLocalAddress()
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            isError = true,
                            errorMessage = result.message
                        )
                    }
                }

            }
        }
    }


    private fun removeFromWishlist(
        productId: String
    ) {
        viewModelScope.launch {
            removeFromWishlistUseCase.execute(
                productId = productId
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(
                            foodItemUpdateInfo = FoodItemUpdateInfo(
                                id = productId,
                                isLoading = true
                            )
                        )
                    }

                    is Resource.Success -> {
                        val isError = result.data?.status == Status.error.name
                        if (!isError) {
                            addLog(
                                LogRequest(
                                    type = LogType.actionPerform,
                                    event = "remove from wishlist",
                                    page_name = "/home",
                                    product_id = productId
                                )
                            )
                        }
                        val data = result.data
                        state = state.copy(
                            isLoading = false,
                            isError = isError,
                            message = if (isError) data!!.message else null,
                            deleteFromWishlistModel = result.data,
                            foodItemUpdateInfo = state.foodItemUpdateInfo?.copy(
                                isLoading = false,
                                added = false
                            )
                        )

                        getWishlist()

                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            isError = true,
                            message = result.message,
                            foodItemUpdateInfo = state.foodItemUpdateInfo?.copy(
                                isLoading = false,
                                added = false
                            )
                        )
                    }
                }

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
                        getCart()

                        if (!isError) {
                            addAppEvent(context, productId, quantity)
                            addLog(
                                LogRequest(
                                    type = LogType.addToCart,
                                    event = "update Cart",
                                    page_name = "/home",
                                    product_id = productId
                                )
                            )
                        }
                        state.copy(
                            isLoading = false,
                            isError = isError,
                            //errorMessage = "Something went Wrong",
                        )

                    }

                    is Resource.Error ->
                        state.copy(
                            isLoading = false,
                            isError = true,
                            errorMessage = result.message,
                            showErrorMessage = true
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
                            addLog(
                                LogRequest(
                                    type = LogType.actionPerform,
                                    event = "delete Cart",
                                    page_name = "/home",
                                    product_id = productId
                                )
                            )

                        getCart()

                        state.copy(
                            isLoading = false,
                            isError = isError,
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

    private fun getCart() {
        viewModelScope.launch {
            cartUseCase.execute().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        //state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        val isError = result.data?.status == Status.error.name
                        if (!isError)
                            T2PApp.cartCount = result.data!!.result.size
                        state =
                            state.copy(
                                isLoading = false,
                                cartData = result.data,
                                isError = isError,
                                errorMessage = if (isError) "Something Went wrong" else "",
                                noAddressFound = false
                            )

                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            isError = true,
                            errorMessage = result.message
                        )
                    }
                }

            }
        }
    }

    private fun getLocalAddress() {
        viewModelScope.launch {
            val localAddress = userPref.getAddress()
            val defaultAddress = userPref.getDefaultAddress()
            if (localAddress != null || defaultAddress != null) {
                state = state.copy(
                    defaultAddress = defaultAddress,
                    localAddress = localAddress,
                    isLoading = true
                )
                getCart()
            } else {
                state = state.copy(noAddressFound = true)
            }
        }
    }

    private fun getAddress() {
        viewModelScope.launch {
            allAddressUseCase.execute(
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(addressLoader = true)
                    }

                    is Resource.Success -> {
                        val isError = result.data?.status == Status.error.name
                        state =
                            state.copy(
                                addressLoader = false,
                                isError = isError,
                                message = if (isError) result.data?.message else "",
                                addressListModel = result.data
                            )

                        /* if (!isError) {
                             if (state.defaultAddress == null && result.data != null && result.data.result.isNotEmpty()) {
                             } else {
                                 if (result.data != null && result.data.result.isEmpty()) {
                                     state = state.copy(noAddressFound = true)
                                 } else if (state.cartData == null)
                                     getCart()
                             }

                         }*/
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            addressLoader = false,
                            isError = true,
                            errorMessage = result.message
                        )
                    }
                }

            }
        }
    }

    private fun addToWishlist(productId: String) {
        viewModelScope.launch {
            addToWishlistUseCase.execute(
                productId
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(
                            foodItemUpdateInfo = FoodItemUpdateInfo(
                                id = productId,
                                isLoading = true
                            )
                        )
                    }

                    is Resource.Success -> {
                        val isError = result.data?.status == Status.error.name

                        if (!isError)
                            addLog(
                                LogRequest(
                                    type = LogType.addToWishlist,
                                    event = "Add to wishlist",
                                    page_name = "/home",
                                    product_id = productId
                                )
                            )


                        getWishlist()
                        state =
                            state.copy(
                                isLoading = false,
                                addToWishlistResponse = result.data,
                                message = result.data?.message,
                                isError = isError,
                                errorMessage = result.data?.message,
                                foodItemUpdateInfo = state.foodItemUpdateInfo?.copy(
                                    isLoading = false,
                                    added = result.data?.status == Status.success.name
                                )
                            )
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            isError = true,
                            errorMessage = result.message,
                            foodItemUpdateInfo = state.foodItemUpdateInfo?.copy(
                                isLoading = false,
                                added = false
                            )
                        )
                    }
                }

            }
        }
    }

    private fun addToCart(context: Context, productId: String) {
        viewModelScope.launch {
            addToCartUseCase.execute(
                productId
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                    }

                    is Resource.Success -> {
                        val isError = result.data?.status == Status.error.name
                        if (!isError)
                            addLog(
                                LogRequest(
                                    type = LogType.addToCart,
                                    event = "Add to cart",
                                    page_name = "/home",
                                    product_id = productId
                                )
                            )
                        state =
                            state.copy(
                                isLoading = false,
                                addToCartResponse = result.data,
                                errorMessage = result.data?.message,
                                message = result.data?.message,
                                cartError = isError,
                            )
                        if (!isError)
                            addAppEvent(context, productId, 1)
                        getCart()
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            isError = true,
                            errorMessage = result.message,
                            showErrorMessage = true
                        )
                    }
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

}