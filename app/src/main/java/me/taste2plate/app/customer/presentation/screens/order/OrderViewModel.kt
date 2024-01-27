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
import me.taste2plate.app.customer.data.UserPref
import me.taste2plate.app.customer.domain.model.custom.LogRequest
import me.taste2plate.app.customer.domain.model.custom.LogType
import me.taste2plate.app.customer.domain.model.user.GharKaKhanaOrderList
import me.taste2plate.app.customer.domain.model.user.OrderListModel
import me.taste2plate.app.customer.domain.use_case.analytics.AddLogUseCase
import me.taste2plate.app.customer.domain.use_case.user.order.CancelOrderUseCase
import me.taste2plate.app.customer.domain.use_case.user.order.GharKaKhanaOrderListUseCase
import me.taste2plate.app.customer.domain.use_case.user.order.OrderListUseCase
import me.taste2plate.app.customer.domain.use_case.user.order.OrderUpdateUseCase
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val userPref: UserPref,
    private val addLogUseCase: AddLogUseCase,
    private val orderListUseCase: OrderListUseCase,
    private val orderUpdateUseCase: OrderUpdateUseCase,
    private val cancelOrderUseCase: CancelOrderUseCase,
    private val gharKaKhanaOrderListUseCase: GharKaKhanaOrderListUseCase,
) : ViewModel() {

    var state by mutableStateOf(OrderState())
    var selectedOrder: OrderListModel.Result? = null
    var selectedGharKaKhanOrder: GharKaKhanaOrderList.Result? = null

    init {
        getOrders()
        getSettings()
    }

    fun onEvent(event: OrderEvent) {
        when (event) {
            is OrderEvent.GetOrderList -> {}
            is OrderEvent.UpdateState -> {
                state = state.copy(
                    cancelOrder = null
                )
            }

            is OrderEvent.CancelOrder -> {
                cancelOrder()
            }

            is OrderEvent.GetOrderUpdate -> {
                getOrderUpdates()
            }

            is OrderEvent.AddLog -> {
                addLog(event.logRequest)
            }

            is OrderEvent.ChangeTab -> {
                state = state.copy(selectedTab = event.index)
                if (event.index == 1 && state.gharKaKhanaOrderList.isEmpty()) {
                    gharKaKhanaOrderList()
                }
            }
        }
    }

    private fun addLog(logRequest: LogRequest) {
        viewModelScope.launch {
            addLogUseCase.execute(logRequest)
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

    private fun gharKaKhanaOrderList() {
        viewModelScope.launch {
            gharKaKhanaOrderListUseCase.execute().collect { result ->
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
                                gharKaKhanaOrderList = if (data.result.isNullOrEmpty()) emptyList() else data.result
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
        if (selectedOrder != null) {
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

    private fun cancelOrder() {
        viewModelScope.launch {
            cancelOrderUseCase.execute(selectedOrder!!.id).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        val data = result.data!!
                        val isError = data.status == Status.error.name

                        if (!isError) {
                            addLog(
                                LogRequest(
                                    type = LogType.actionPerform,
                                    event = "cancel order",
                                    page_name = "/order",
                                    order_id = selectedOrder?.id ?: ""
                                )
                            )
                        }
                        state =
                            state.copy(
                                isLoading = false,
                                isError = isError,
                                message = if (isError) result.data.message else null,
                                cancelOrder = data
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