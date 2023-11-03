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
import me.taste2plate.app.customer.domain.use_case.CartUseCase
import me.taste2plate.app.customer.domain.use_case.HomeUseCase
import me.taste2plate.app.customer.domain.use_case.WishlistUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userPref: UserPref,
    private val homeUseCase: HomeUseCase,
    private val wishlistUseCase: WishlistUseCase,
    private val cartUseCase: CartUseCase,
) : ViewModel() {

    var state by mutableStateOf(HomeState())

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.GetHome -> {
                getHomeData()
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
                        getWishlist()
                        state = if (result.data?.status == Status.success.name)
                            state.copy(
                                isLoading = false,
                                homeData = result.data
                            )
                        else
                            state.copy(
                                isLoading = false,
                                isError = true,
                                error = "Something Went wrong"
                            )
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            isError = true,
                            error = result.message
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
                        getCart()
                        state = if (result.data?.status == Status.success.name) {
                            T2PApp.wishlistCount = result.data.result.size
                            state.copy(
                                isLoading = false,
                                wishListData = result.data
                            )
                        } else
                            state.copy(
                                isLoading = false,
                                isError = true,
                                error = "Something Went wrong"
                            )
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            isError = true,
                            error = result.message
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
                                error = "Something Went wrong"
                            )
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            isError = true,
                            error = result.message
                        )
                    }
                }

            }
        }
    }

}