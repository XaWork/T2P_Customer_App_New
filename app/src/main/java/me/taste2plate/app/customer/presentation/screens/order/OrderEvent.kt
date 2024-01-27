package me.taste2plate.app.customer.presentation.screens.order

import android.content.Context
import me.taste2plate.app.customer.domain.model.custom.LogRequest
import me.taste2plate.app.customer.domain.model.user.address.AddressListModel
import me.taste2plate.app.customer.presentation.screens.citybrand.CityBrandEvents

sealed class OrderEvent {
    object GetOrderList : OrderEvent()
    object UpdateState : OrderEvent()
    object GetOrderUpdate : OrderEvent()
    object CancelOrder : OrderEvent()

    data class ChangeTab(
        val index: Int
    ): OrderEvent()


    data class AddLog(
        val logRequest: LogRequest
    ) : OrderEvent()
}
