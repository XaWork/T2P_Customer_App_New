package me.taste2plate.app.customer.presentation.screens.bulk_order

import me.taste2plate.app.customer.domain.model.CityBrandModel
import me.taste2plate.app.customer.domain.model.CityListModel
import me.taste2plate.app.customer.domain.model.user.CartModel
import me.taste2plate.app.customer.domain.model.user.CommonResponse

data class BulkOrderState(
    val isLoading:Boolean = false,
    val isError:Boolean = false,
    val message: String? = null,
    val cities: List<CityBrandModel.Result> = emptyList(),
    val createOrder: CommonResponse? = null
)
