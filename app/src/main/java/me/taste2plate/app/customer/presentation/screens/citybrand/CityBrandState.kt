package me.taste2plate.app.customer.presentation.screens.citybrand

import me.taste2plate.app.customer.domain.mapper.CommonForItem

data class CityBrandState(
    val isLoading:Boolean = false,
    val isError: Boolean = false,
    val message: String? = null,
    val itemList: List<CommonForItem> = emptyList(),
)
