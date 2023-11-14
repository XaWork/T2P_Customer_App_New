package me.taste2plate.app.customer.presentation.screens.order

import me.taste2plate.app.customer.domain.model.HomeModel
import me.taste2plate.app.customer.domain.model.user.CartModel
import me.taste2plate.app.customer.domain.model.user.CommonResponse
import me.taste2plate.app.customer.domain.model.user.OrderListModel
import me.taste2plate.app.customer.domain.model.user.WishListModel
import me.taste2plate.app.customer.domain.model.user.address.AddressListModel
import me.taste2plate.app.customer.presentation.screens.home.FoodItemUpdateInfo

data class OrderState(
    val isLoading: Boolean = false,
    val message: String? = null,
    val isError: Boolean = false,
    val orderList: List<OrderListModel.Result> = emptyList()
)

