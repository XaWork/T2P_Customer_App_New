package me.taste2plate.app.customer.presentation.screens.address

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.taste2plate.app.customer.data.Resource
import me.taste2plate.app.customer.data.Status
import me.taste2plate.app.customer.domain.model.StateListModel
import me.taste2plate.app.customer.domain.model.custom.City
import me.taste2plate.app.customer.domain.use_case.CityListByStateUseCase
import me.taste2plate.app.customer.domain.use_case.StateListUseCase
import me.taste2plate.app.customer.domain.use_case.ZipListUseCase
import me.taste2plate.app.customer.domain.use_case.user.address.AddAddressUseCase
import me.taste2plate.app.customer.domain.use_case.user.address.AllAddressUseCase
import me.taste2plate.app.customer.domain.use_case.user.address.DeleteAddressUseCase
import me.taste2plate.app.customer.domain.use_case.user.address.EditAddressUseCase
import me.taste2plate.app.customer.presentation.widgets.RadioButtonInfo
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val addressUseCase: AllAddressUseCase,
    private val deleteAddressUseCase: DeleteAddressUseCase,
    private val stateListUseCase: StateListUseCase,
    private val cityListUseCase: CityListByStateUseCase,
    private val zipListUseCase: ZipListUseCase,
    private val addAddressUseCase: AddAddressUseCase,
    private val editAddressUseCase: EditAddressUseCase
) : ViewModel() {

    var state by mutableStateOf(AddressState())
    var addressIndex: Int = -1
    var latLng: LatLng? by mutableStateOf(null)

    var fullName = mutableStateOf("")
    var phone = mutableStateOf("")
    var address1 = mutableStateOf("")
    var address2A = mutableStateOf("")
    private var country = mutableStateOf("India")
    var stateA = mutableStateOf("")
    var cityA = mutableStateOf("")
    var pincodeA = mutableStateOf("")
    var pinCodeExpanded by mutableStateOf(false)
    var filterPinList = mutableStateListOf<String>()
    var landmarkA = mutableStateOf("")
    val addressTypes = listOf(
        RadioButtonInfo(
            id = 1,
            text = "Home"
        ),
        RadioButtonInfo(
            id = 2,
            text = "Work"
        ),
        RadioButtonInfo(
            id = 3,
            text = "Other"
        )
    )
    var addressType = mutableStateOf(addressTypes[0])

    init {
        getAddressList()
    }

    fun onEvent(event: AddressEvents) {
        when (event) {
            is AddressEvents.GetAddressList -> {
                getAddressList()
            }

            is AddressEvents.SetData -> {
                setData()
            }

            is AddressEvents.GetCityList -> {
                getCityList()
            }

            is AddressEvents.AddAddress -> {
                if (validateFrom())
                    addAddress()
                else
                    state = state.copy(isError = true, message = "Fill all mandatory fields.")
            }

            is AddressEvents.EditAddress -> {
                if (validateFrom())
                    editAddress()
                else
                    state = state.copy(isError = true, message = "Fill all mandatory fields.")
            }

            is AddressEvents.GetZipList -> {
                getZipList()
            }

            is AddressEvents.UpdateState -> {
                state = state.copy(isError = false, message = null)
            }

            is AddressEvents.StoreAddressId -> {
                addressIndex = event.addressId
            }

            is AddressEvents.DeleteAddress -> {
                deleteAddress()
            }

            is AddressEvents.SearchPin -> {
                searchPin(event.query)
            }
        }
    }

    private fun searchPin(query: String) {
        filterPinList.clear()
        pinCodeExpanded = false
        Log.e("Search pin", "Query is $query")
        for (item in state.zipList) {
            if (item.name.contains(query, ignoreCase = true)) {
                pinCodeExpanded = true
                Log.e("Search pin", "zip item  is ${item.name}")
                filterPinList += item.name
            }else{
                pinCodeExpanded = false
            }
        }
    }

    private fun setData() {
        if (addressIndex != -1) {
            val addressInfo = state.addressList[addressIndex]
            addressInfo.run {
                fullName.value = contactName
                phone.value = contactMobile
                address1.value = address
                address2A.value = address2
                stateA.value = state.name
                cityA.value = city.name
                pincodeA.value = pincode
                landmarkA.value = landmark
                addressType.value = when (title.lowercase()) {
                    "home" -> addressTypes[0]
                    "work" -> addressTypes[1]
                    "other" -> addressTypes[2]
                    else -> addressTypes[0]
                }
            }
        } /*else {
            fullName.value = ""
            phone.value = ""
            address1.value = ""
            address2A.value = ""
            stateA.value = ""
            cityA.value = ""
            pincodeA.value = ""
            landmarkA.value = ""
            addressType.value = addressTypes[0]
        }*/
        getStateList()
    }


    private fun getStateList(
        isLoading: Boolean = true
    ) {
        viewModelScope.launch {
            stateListUseCase.execute().collect { result ->
                state = when (result) {
                    is Resource.Loading -> state.copy(isLoading = isLoading)
                    is Resource.Success -> {
                        val data = result.data
                        val isError = data?.status == Status.error.name

                        /* if (!isError) {
                             stateA.value = data!!.result[0].name
                         }*/

                        state.copy(
                            isLoading = false,
                            isError = isError,
                            stateList = if (isError) emptyList() else data!!.result
                        )
                    }

                    is Resource.Error ->
                        state.copy(
                            isLoading = false,
                            isError = true,
                            message = result.message
                        )
                }

            }
        }
    }

    private fun getItemId(
        city: Boolean = false,
        cityList: List<City>? = null,
        stateList: List<StateListModel.Result>? = null,
    ): String {
        return if (city && state.cityList.isEmpty()) state.addressList[addressIndex].city.id
        else if (city) cityList!!.find { it.name == cityA.value }!!.id
        else stateList!!.find { it.name == stateA.value }!!.id

    }

    private fun getCityList(
    ) {
        //change value
        cityA.value = ""
        pincodeA.value = ""

        val stateId = getItemId(stateList = state.stateList)

        viewModelScope.launch {
            cityListUseCase.execute(stateId).collect { result ->
                state = when (result) {
                    is Resource.Loading -> state.copy(isLoading = false)
                    is Resource.Success -> {
                        val data = result.data
                        val isError = data?.status == Status.error.name
                        state.copy(
                            isLoading = false,
                            isError = isError,
                            cityList = if (isError) emptyList() else data!!.result
                        )
                    }

                    is Resource.Error ->
                        state.copy(
                            isLoading = false,
                            isError = true,
                            message = result.message
                        )
                }

            }
        }
    }

    private fun getZipList() {
        pincodeA.value = ""

        val cityId = getItemId(
            city = true,
            cityList = state.cityList
        )

        viewModelScope.launch {
            zipListUseCase.execute(cityId).collect { result ->
                state = when (result) {
                    is Resource.Loading -> state.copy(isLoading = false)
                    is Resource.Success -> {
                        val data = result.data
                        val isError = data?.status == Status.error.name
                        state.copy(
                            isLoading = false,
                            isError = isError,
                            zipList = if (isError) emptyList() else data!!.result
                        )
                    }

                    is Resource.Error ->
                        state.copy(
                            isLoading = false,
                            isError = true,
                            message = result.message
                        )
                }

            }
        }
    }

    private fun getAddressList(
        isLoading: Boolean = true
    ) {
        viewModelScope.launch {
            addressUseCase.execute().collect { result ->
                state = when (result) {
                    is Resource.Loading -> state.copy(isLoading = isLoading)
                    is Resource.Success -> {
                        val data = result.data
                        val isError = data?.status == Status.error.name
                        state.copy(
                            isLoading = false,
                            isError = isError,
                            message = if (isError) data?.message else null,
                            addressList = if (isError) emptyList() else data!!.result
                        )
                    }

                    is Resource.Error ->
                        state.copy(
                            isLoading = false,
                            isError = true,
                            message = result.message
                        )
                }

            }
        }
    }

    private fun validateFrom(): Boolean {
        return fullName.value.isNotEmpty() &&
                phone.value.isNotEmpty() &&
                address1.value.isNotEmpty() &&
                address2A.value.isNotEmpty() &&
                country.value.isNotEmpty() &&
                stateA.value.isNotEmpty() &&
                cityA.value.isNotEmpty() &&
                pincodeA.value.isNotEmpty()
    }

    private fun deleteAddress() {
        val addressId = state.addressList[addressIndex].id
        addressIndex = -1
        viewModelScope.launch {
            deleteAddressUseCase.execute(addressId).collect { result ->
                state = when (result) {
                    is Resource.Loading -> state.copy(isLoading = false)
                    is Resource.Success -> {
                        val data = result.data
                        val isError = data?.status == Status.error.name

                        if (!isError)
                            getAddressList(isLoading = false)

                        state.copy(
                            isLoading = false,
                            isError = isError,
                            message = data?.message,
                            deleteAddressResponse = data
                        )
                    }

                    is Resource.Error ->
                        state.copy(
                            isLoading = false,
                            isError = true,
                            message = result.message
                        )
                }

            }
        }
    }

    private fun addAddress() {
        viewModelScope.launch {
            addAddressUseCase.execute(
                name = fullName.value,
                phone = phone.value,
                city = getItemId(city = true, cityList = state.cityList),
                state = getItemId(stateList = state.stateList),
                pincode = pincodeA.value,
                addressLine = address1.value,
                postOffice = landmarkA.value,
                secondary = address2A.value,
                lat = latLng!!.latitude,
                lng = latLng!!.longitude,
                type = addressType.value.text
            ).collect { result ->
                state = when (result) {
                    is Resource.Loading -> state.copy(isLoading = true)
                    is Resource.Success -> {
                        val data = result.data
                        val isError = data?.status == Status.error.name

                        state.copy(
                            isLoading = false,
                            isError = isError,
                            message = data?.message,
                            addAddressResponse = data
                        )
                    }

                    is Resource.Error ->
                        state.copy(
                            isLoading = false,
                            isError = true,
                            message = result.message
                        )
                }

            }
        }
    }

    private fun editAddress() {
        viewModelScope.launch {
            editAddressUseCase.execute(
                addressId = state.addressList[addressIndex].id,
                name = fullName.value,
                phone = phone.value,
                city = getItemId(city = true, cityList = state.cityList),
                state = getItemId(stateList = state.stateList),
                pincode = pincodeA.value,
                addressLine = address1.value,
                postOffice = landmarkA.value,
                secondary = address2A.value,
                lat = latLng!!.latitude,
                lng = latLng!!.longitude,
                type = addressType.value.text
            ).collect { result ->
                state = when (result) {
                    is Resource.Loading -> state.copy(isLoading = true)
                    is Resource.Success -> {
                        val data = result.data
                        val isError = data?.status == Status.error.name

                        state.copy(
                            isLoading = false,
                            isError = isError,
                            message = data?.message,
                            editAddressResponse = data
                        )
                    }

                    is Resource.Error ->
                        state.copy(
                            isLoading = false,
                            isError = true,
                            message = result.message
                        )
                }

            }
        }
    }


}