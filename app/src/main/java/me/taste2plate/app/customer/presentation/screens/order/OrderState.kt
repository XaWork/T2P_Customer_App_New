package me.taste2plate.app.customer.presentation.screens.order

import me.taste2plate.app.customer.domain.model.SettingsModel
import me.taste2plate.app.customer.domain.model.user.CommonResponse
import me.taste2plate.app.customer.domain.model.user.GharKaKhanaOrderList
import me.taste2plate.app.customer.domain.model.user.OrderListModel
import me.taste2plate.app.customer.domain.model.user.OrderUpdateModel

data class OrderState(
    val isLoading: Boolean = false,
    val message: String? = null,
    val serverTime: String? = null,
    val isError: Boolean = false,
    val selectedTab: Int = 0,
    val tabs : List<String> = listOf("T2P", "Ghar Ka Khana"),
    val setting: SettingsModel.Result? = null,
    val orderList: List<OrderListModel.Result> = emptyList(),
    val gharKaKhanaOrderList: List<GharKaKhanaOrderList.Result?> = emptyList(),
    val orderUpdates: List<OrderUpdateModel.Result> = emptyList(),
    val cancelOrder: CommonResponse? = null,
)

