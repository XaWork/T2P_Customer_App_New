package me.taste2plate.app.customer.presentation.screens.citybrand

import me.taste2plate.app.customer.domain.mapper.CommonForItem
import me.taste2plate.app.customer.domain.model.custom.LogRequest
import me.taste2plate.app.customer.presentation.screens.home.CityBrandScreens

sealed class CityBrandEvents {
    data class GetData(
        val screen: CityBrandScreens
    ) : CityBrandEvents()
    data class AddLog(
        val logRequest: LogRequest
    ) : CityBrandEvents()
    object GetSubCategory : CityBrandEvents()

    data class SetSelectedItem(
        val item: CommonForItem
    ): CityBrandEvents()
}
