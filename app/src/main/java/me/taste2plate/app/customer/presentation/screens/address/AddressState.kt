package me.taste2plate.app.customer.presentation.screens.address

import me.taste2plate.app.customer.domain.model.StateListModel
import me.taste2plate.app.customer.domain.model.ZipListModel
import me.taste2plate.app.customer.domain.model.custom.City
import me.taste2plate.app.customer.domain.model.user.CommonResponse
import me.taste2plate.app.customer.domain.model.user.address.AddressListModel

data class AddressState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val message: String? = null,
    val addressList: List<AddressListModel.Result> = emptyList(),
    val stateList: List<StateListModel.Result> = emptyList(),
    val cityList: List<City> = emptyList(),
    val zipList: List<ZipListModel.Result> = emptyList(),
    val deleteAddressResponse: CommonResponse? = null,
    val addAddressResponse: CommonResponse? = null,
    val editAddressResponse: CommonResponse? = null
)
