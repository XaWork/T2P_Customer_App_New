package me.taste2plate.app.customer.presentation.screens.order

import android.content.Context
import me.taste2plate.app.customer.domain.model.user.address.AddressListModel

sealed class OrderEvent {
    object GetOrderList : OrderEvent()
}
