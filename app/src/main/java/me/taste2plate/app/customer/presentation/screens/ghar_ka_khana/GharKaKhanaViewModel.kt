package me.taste2plate.app.customer.presentation.screens.ghar_ka_khana

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.taste2plate.app.customer.data.Resource
import me.taste2plate.app.customer.data.Status
import me.taste2plate.app.customer.data.UserPref
import me.taste2plate.app.customer.domain.use_case.custom.GharKaKhanaCategoryUseCase
import me.taste2plate.app.customer.domain.use_case.custom.GharKaKhanaSubCategoryUseCase
import me.taste2plate.app.customer.domain.use_case.user.GharKaKhanaAddToCartUseCase
import me.taste2plate.app.customer.domain.use_case.user.GharKaKhanaCheckoutUseCase
import me.taste2plate.app.customer.domain.use_case.user.GharKaKhanaConfirmCheckoutUseCase
import me.taste2plate.app.customer.domain.use_case.user.GharKaKhanaDeleteCartUseCase
import me.taste2plate.app.customer.domain.use_case.user.GharKaKhanaFetchCartUseCase
import me.taste2plate.app.customer.domain.use_case.user.address.AllAddressUseCase
import me.taste2plate.app.customer.presentation.screens.checkout.DeliveryType
import javax.inject.Inject

@HiltViewModel
class GharKaKhanaViewModel @Inject constructor(
    var userPref: UserPref,
    private var gharKaKhanaCategoryUseCase: GharKaKhanaCategoryUseCase,
    private var gharKaKhanaSubCategoryUseCase: GharKaKhanaSubCategoryUseCase,
    private val allAddressUseCase: AllAddressUseCase,
    private val gkkAddToCartUseCase: GharKaKhanaAddToCartUseCase,
    private val gkkFetchCartUseCase: GharKaKhanaFetchCartUseCase,
    private val gharKaKhanaCheckoutUseCase: GharKaKhanaCheckoutUseCase,
    private val gkkDeleteCartUseCase: GharKaKhanaDeleteCartUseCase,
    private val gharKaKhanaConfirmCheckoutUseCase: GharKaKhanaConfirmCheckoutUseCase
) : ViewModel() {

    var state by mutableStateOf(GharKaKhanaState())
    var category by mutableStateOf("")
    var subCategory by mutableStateOf("")
    var checked by mutableStateOf(true)
    var deliveryType by mutableStateOf(DeliveryType.Standard)
    var remarks by mutableStateOf("")
    var selectedDate by mutableStateOf("")
    var selectedTimeSlot by mutableStateOf("")
    var productName by mutableStateOf("")
    var weight by mutableStateOf("")
    val pickupTimeSlots = listOf(
        "08:00 AM - 12:00 PM",
        "12:00 PM - 04:00 PM",
        "04:00 PM - 08:00 PM"
    )

    init {
        getUser()
    }


    fun onEvent(event: GharKaKhanaEvent) {
        when (event) {
            is GharKaKhanaEvent.SetPDLocation -> {
                state = when (event.pdLocationType) {
                    PDLocation.Pickup -> {
                        if (state.destinationLocation != event.location)
                            state.copy(pickupLocation = event.location)
                        else state.copy(
                            isError = true,
                            message = "Both address should not be similar."
                        )
                    }

                    PDLocation.Destination -> {
                        if (state.pickupLocation != event.location)
                            state.copy(destinationLocation = event.location)
                        else state.copy(
                            isError = true,
                            message = "Both address should not be similar."
                        )
                    }
                }
            }

            GharKaKhanaEvent.GetAddress -> {
                getAddress()
            }

            GharKaKhanaEvent.UpdateState -> {
                state = state.copy(isError = false, message = null, moveToCheckout = false)
            }

            GharKaKhanaEvent.AddToCart -> {
                if (category.isEmpty() || subCategory.isEmpty() || productName.isEmpty() || weight.isEmpty()) {
                    state = state.copy(isError = true, message = "Fill all data")
                } else {
                    addToCart()
                }
            }

            is GharKaKhanaEvent.DeleteCart -> {
                deleteCart(event.id)
            }

            GharKaKhanaEvent.BookNow -> {
                if (state.pickupLocation == null || state.destinationLocation == null)
                    state = state.copy(
                        isError = true,
                        message = "Choose Pickup and Destination Location."
                    )
                else if (state.cartItems.isEmpty())
                    state = state.copy(isError = true, message = "Add at least one item.")
                else if (selectedDate.isEmpty() || selectedTimeSlot.isEmpty())
                    state = state.copy(isError = true, message = "Select pickup date and time.")
                else
                    checkout()
            }

            GharKaKhanaEvent.ConfirmCheckout -> {
                if (state.checkout != null) confirmCheckout()
            }
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            state = state.copy(user = userPref.getUser())
        }
        getCategory()
    }

    private fun getCategory() {
        viewModelScope.launch {
            gharKaKhanaCategoryUseCase.execute().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        val isError = result.data?.status == Status.error.name
                        if (!isError)
                            getSubCategory()

                        state =
                            state.copy(
                                isLoading = false,
                                category = result.data!!.result,
                                isError = isError,
                                message = if (isError) "No category found" else null
                            )

                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            isError = true,
                            message = result.message
                        )
                    }
                }

            }
        }
    }

    private fun getSubCategory() {
        viewModelScope.launch {
            gharKaKhanaSubCategoryUseCase.execute().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        // state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        val isError = result.data?.status == Status.error.name
                        state =
                            state.copy(
                                isLoading = false,
                                subCategory = result.data!!.result,
                                isError = isError,
                                message = if (isError) "No category found" else null
                            )

                        if (!isError)
                            fetchCart()

                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            isError = true,
                            message = result.message
                        )
                    }
                }

            }
        }
    }

    private fun getAddress() {
        viewModelScope.launch {
            allAddressUseCase.execute(
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(addressLoader = true)
                    }

                    is Resource.Success -> {
                        val isError = result.data?.status == Status.error.name
                        state =
                            state.copy(
                                addressLoader = false,
                                isError = isError,
                                message = if (isError) result.data?.message else "",
                                addressListModel = if (result.data != null) result.data.result else emptyList()
                            )
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            addressLoader = false,
                            isError = true,
                            message = result.message
                        )
                    }
                }

            }
        }
    }

    private fun fetchCart() {
        viewModelScope.launch {
            gkkFetchCartUseCase.execute().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        //state = state.copy(addressLoader = true)
                    }

                    is Resource.Success -> {
                        val isError = result.data?.status == Status.error.name
                        state =
                            state.copy(
                                buttonLoader = false,
                                isError = isError,
                                message = if (isError) result.data?.message else "",
                                cartItems = if (result.data != null) result.data.result else emptyList()
                            )
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            buttonLoader = false,
                            isError = true,
                            message = result.message
                        )
                    }
                }

            }
        }
    }

    private fun deleteCart(itemId: String) {
        viewModelScope.launch {
            gkkDeleteCartUseCase.execute(itemId).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(buttonLoader = true)
                    }

                    is Resource.Success -> {
                        val isError = result.data?.status == Status.error.name
                        state =
                            state.copy(
                                //buttonLoader = false,
                                isError = isError,
                                message = if (isError) result.data?.message else "",
                            )

                        if (!isError)
                            fetchCart()
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            buttonLoader = false,
                            isError = true,
                            message = result.message
                        )
                    }
                }

            }
        }
    }

    private fun addToCart() {
        val categoryId = state.category.find { it.name == category }!!.id
        val subCategoryId = state.subCategory.find { it.name == subCategory }!!.id

        viewModelScope.launch {
            gkkAddToCartUseCase.execute(
                productName,
                categoryId,
                subCategoryId,
                weight
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(buttonLoader = true)
                    }

                    is Resource.Success -> {
                        val isError = result.data?.status == Status.error.name
                        state =
                            state.copy(
                                ///buttonLoader = false,
                                isError = isError,
                                message = if (isError) result.data?.message else "",
                            )

                        if (!isError) {
                            category = ""
                            subCategory = ""
                            productName = ""
                            weight  = ""
                            fetchCart()
                        }
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            buttonLoader = false,
                            isError = true,
                            message = result.message
                        )
                    }
                }

            }
        }
    }


    private fun checkout() {
        viewModelScope.launch {
            gharKaKhanaCheckoutUseCase.execute(
                state.pickupLocation!!.id,
                state.destinationLocation!!.id,
                selectedDate + "T18:04:29.820+00:00",
                selectedTimeSlot,
                deliveryType.name.lowercase()
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(bookButtonLoader = true)
                    }

                    is Resource.Success -> {
                        val isError = result.data?.status == Status.error.name
                        state =
                            state.copy(
                                bookButtonLoader = false,
                                isError = isError,
                                message = if (isError) result.data?.message else null,
                                checkout = result.data,
                                moveToCheckout = !isError
                            )
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            bookButtonLoader = false,
                            isError = true,
                            message = result.message
                        )
                    }
                }
            }
        }
    }
    private fun confirmCheckout() {
        viewModelScope.launch {
            gharKaKhanaConfirmCheckoutUseCase.execute(
                state.pickupLocation!!.id,
                state.destinationLocation!!.id,
                selectedDate + "T18:04:29.820+00:00",
                selectedTimeSlot,
                deliveryType.name.lowercase()
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(bookButtonLoader = true)
                    }

                    is Resource.Success -> {
                        val isError = result.data?.status == Status.error.name
                        state =
                            state.copy(
                                bookButtonLoader = false,
                                isError = isError,
                                message = if (isError) result.data?.message else null,
                                confirmCheckout = result.data,
                            )
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            bookButtonLoader = false,
                            isError = true,
                            message = result.message
                        )
                    }
                }

            }
        }
    }


}