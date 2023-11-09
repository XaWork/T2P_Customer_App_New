package me.taste2plate.app.customer.presentation.screens.address

sealed class AddressEvents {
    object GetAddressList : AddressEvents()
    object AddAddress : AddressEvents()
    object EditAddress : AddressEvents()
    object UpdateState : AddressEvents()
    data class StoreAddressId(
        val addressId:Int
    ) : AddressEvents()
    object DeleteAddress : AddressEvents()
    object SetData : AddressEvents()
    object GetCityList : AddressEvents()
    object GetZipList  : AddressEvents()
}
