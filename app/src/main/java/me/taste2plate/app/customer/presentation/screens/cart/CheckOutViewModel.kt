package me.taste2plate.app.customer.presentation.screens.cart

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
import me.taste2plate.app.customer.domain.use_case.user.cart.CartUseCase
import me.taste2plate.app.customer.domain.use_case.user.cart.DeleteCartUseCase
import me.taste2plate.app.customer.domain.use_case.user.cart.UpdateCartUseCase
import javax.inject.Inject

@HiltViewModel
class CheckOutViewModel @Inject constructor(
    private val cartUseCase: CartUseCase,
    private val updateCartUseCase: UpdateCartUseCase,
    private val deleteCartUseCase: DeleteCartUseCase,
) : ViewModel() {

    var state by mutableStateOf(CheckoutState())

    init {
        getCart()
    }

    fun onEvent(event: CheckoutEvents) {
        when (event) {
            is CheckoutEvents.GetCart -> {}
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

                        T2PApp.cartCount = if(data!!.result.isEmpty()) 0 else data.result.size
                        state.copy(
                            isLoading = false,
                            /*isError = data?.status == Status.error.name,
                            errorMessage = "Something went Wrong"*/
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


}