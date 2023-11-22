package me.taste2plate.app.customer.presentation.screens.order

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.taste2plate.app.customer.data.Resource
import me.taste2plate.app.customer.data.Status
import me.taste2plate.app.customer.data.UserPref
import me.taste2plate.app.customer.domain.model.user.OrderListModel
import me.taste2plate.app.customer.domain.use_case.user.order.OrderListUseCase
import me.taste2plate.app.customer.domain.use_case.user.order.OrderUpdateUseCase
import okhttp3.Route
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val userPref: UserPref,
    private val orderListUseCase: OrderListUseCase,
    private val orderUpdateUseCase: OrderUpdateUseCase
) : ViewModel() {

    var state by mutableStateOf(OrderState())
    var selectedOrder: OrderListModel.Result? = null

    init {
        getOrders()
        getSettings()
    }

    fun onEvent(event: OrderEvent) {
        when (event) {
            is OrderEvent.GetOrderList -> {}
            is OrderEvent.GetOrderUpdate -> {
                getOrderUpdates()
            }
        }
    }

    private fun getSettings() {
        viewModelScope.launch {
            state = state.copy(setting = userPref.getSettings())
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
                                serverTime = data.serverTime,
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

    private fun getOrderUpdates() {
        viewModelScope.launch {
            orderUpdateUseCase.execute(selectedOrder!!.id).collect { result ->
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
                                orderUpdates = data.result.ifEmpty { emptyList() }
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