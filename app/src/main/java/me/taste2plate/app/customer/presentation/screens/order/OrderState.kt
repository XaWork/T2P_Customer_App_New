package me.taste2plate.app.customer.presentation.screens.order

import me.taste2plate.app.customer.domain.model.user.OrderListModel

data class OrderState(
    val isLoading: Boolean = false,
    val message: String? = null,
    val isError: Boolean = false,
    val orderList: List<OrderListModel.Result> = emptyList()
)

