package me.taste2plate.app.customer.presentation.screens.address

import me.taste2plate.app.customer.domain.model.custom.LogRequest
import me.taste2plate.app.customer.presentation.screens.auth.AuthEvents

sealed class AddressEvents {
    object GetAddressList : AddressEvents()
    object AddAddress : AddressEvents()
    data class AddLog(val logRequest: LogRequest) : AddressEvents()
    object EditAddress : AddressEvents()
    object UpdateState : AddressEvents()
    data class StoreAddressId(
        val addressId:Int
    ) : AddressEvents()

    data class SearchPin(val query: String): AddressEvents()
    object DeleteAddress : AddressEvents()
    object SetData : AddressEvents()
    object GetCityList : AddressEvents()
    object GetZipList  : AddressEvents()
}
