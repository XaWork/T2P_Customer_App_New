package me.taste2plate.app.customer.presentation.screens.home

import androidx.compose.runtime.getValue
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
import me.taste2plate.app.customer.domain.use_case.user.cart.CartUseCase
import me.taste2plate.app.customer.domain.use_case.HomeUseCase
import me.taste2plate.app.customer.domain.use_case.user.address.AllAddressUseCase
import me.taste2plate.app.customer.domain.use_case.user.wishlist.AddToWishlistUseCase
import me.taste2plate.app.customer.domain.use_case.user.wishlist.WishlistUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userPref: UserPref,
    private val homeUseCase: HomeUseCase,
    private val wishlistUseCase: WishlistUseCase,
    private val cartUseCase: CartUseCase,
    private val addToWishlistUseCase: AddToWishlistUseCase,
    private val allAddressUseCase: AllAddressUseCase,
) : ViewModel() {

    var state by mutableStateOf(HomeState())

    init {
        getHomeData()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.GetHome -> {
                getHomeData()
            }

            is HomeEvent.GetAddress -> {
                getAddress()
            }

            is HomeEvent.AddToWishlist -> {
                addToWishlist(event.productId)
            }

            is HomeEvent.SetDefaultAddress -> {
                setDefaultAddress(event.address)
            }

            is HomeEvent.UpdateState -> {
                when {
                    event.changeAddToWishlistResponse -> {
                        state = state.copy(message = null, addToWishlistResponse = null)
                    }
                }
            }
        }
    }

    private fun setDefaultAddress(address: AddressListModel.Result) {
        viewModelScope.launch {
            userPref.saveDefaultAddress(address)
            state = state.copy(defaultAddress = address)
        }
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
            val defaultAddress = userPref.getDefaultAddress()
            if (defaultAddress != null) {
                state = state.copy(defaultAddress = defaultAddress)
                hasDefaultAddress = true
            }
        }

        return hasDefaultAddress
    }

    private fun getWishlist() {
        viewModelScope.launch {
            wishlistUseCase.execute(
                userId = userPref.getUser().id
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        //state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        state = if (result.data?.status == Status.success.name) {
                            if (hasDefaultAddress())
                                getCart()
                            else
                                getAddress()

                            T2PApp.wishlistCount = result.data.result.size
                            state.copy(
                                wishListData = result.data
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
                                errorMessage = if (isError) "Something Went wrong" else ""
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

                        if (!isError) {
                            if (state.defaultAddress != null && result.data != null && result.data.result.isNotEmpty()) {
                                setDefaultAddress(result.data.result[0])
                            }
                            if (state.cartData == null)
                                getCart()
                        }
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
                        getWishlist()
                        state =
                            state.copy(
                                isLoading = false,
                                addToWishlistResponse = result.data,
                                message = result.data?.message,
                                isError = result.data?.status == Status.error.name,
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

}