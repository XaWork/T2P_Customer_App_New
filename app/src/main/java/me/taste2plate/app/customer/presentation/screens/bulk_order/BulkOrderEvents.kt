package me.taste2plate.app.customer.presentation.screens.bulk_order

import me.taste2plate.app.customer.domain.model.custom.LogRequest
import me.taste2plate.app.customer.presentation.screens.citybrand.CityBrandEvents

sealed class BulkOrderEvents {
    object Save : BulkOrderEvents()
    object UpdateState : BulkOrderEvents()
    data class AddLog(
        val logRequest: LogRequest
    ) : BulkOrderEvents()
}
