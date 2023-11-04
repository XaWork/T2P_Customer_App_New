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
import me.taste2plate.app.customer.domain.use_case.user.CartUseCase
import me.taste2plate.app.customer.domain.use_case.HomeUseCase
import me.taste2plate.app.customer.domain.use_case.user.AddToWishlistUseCase
import me.taste2plate.app.customer.domain.use_case.user.wishlist.WishlistUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userPref: UserPref,
    private val homeUseCase: HomeUseCase,
    private val wishlistUseCase: WishlistUseCase,
    private val cartUseCase: CartUseCase,
    private val addToWishlistUseCase: AddToWishlistUseCase,
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

            is HomeEvent.AddToWishlist -> {
                addToWishlist(event.productId)
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
                            T2PApp.wishlistCount = result.data.result.size
                            getCart()
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
            cartUseCase.execute(
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        //state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        state = if (result.data?.status == Status.success.name) {
                            T2PApp.cartCount = result.data.result.size
                            state.copy(
                                isLoading = false,
                                cartData = result.data
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