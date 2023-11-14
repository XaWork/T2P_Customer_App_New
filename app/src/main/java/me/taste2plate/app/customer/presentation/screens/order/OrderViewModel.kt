package me.taste2plate.app.customer.presentation.screens.order

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.taste2plate.app.customer.data.Resource
import me.taste2plate.app.customer.data.Status
import me.taste2plate.app.customer.domain.use_case.user.order.OrderListUseCase
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderListUseCase: OrderListUseCase
) : ViewModel() {

    var state by mutableStateOf(OrderState())

    init {
        getOrders()
    }

    fun onEvent(event: OrderEvent) {
        when (event) {
            is OrderEvent.GetOrderList -> {}
        }
    }

    private fun getOrders() {
        viewModelScope.launch {
            orderListUseCase.execute().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        val data = result.data!!
                        val isError = data.status == Status.error.name
                        state =
                            state.copy(
                                isLoading = false,
                                isError = isError,
                                message = if (isError) result.data.message else "",
                                orderList = data.result.ifEmpty { emptyList() }
                            )
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            isError = true,
                            message = result.message
                        )
                    }
                }
            }
        }
    }


}