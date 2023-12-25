package me.taste2plate.app.customer.presentation.screens.bulk_order

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.taste2plate.app.customer.data.Resource
import me.taste2plate.app.customer.data.Status
import me.taste2plate.app.customer.domain.model.custom.LogRequest
import me.taste2plate.app.customer.domain.model.custom.LogType
import me.taste2plate.app.customer.domain.use_case.CityListUseCase
import me.taste2plate.app.customer.domain.use_case.analytics.AddLogUseCase
import me.taste2plate.app.customer.domain.use_case.user.order.BulkOrderUseCase
import me.taste2plate.app.customer.presentation.screens.home.HomeEvent
import javax.inject.Inject

@HiltViewModel
class BulkOrderViewModel @Inject constructor(
    private val cityListUseCase: CityListUseCase,
    private val addLogUseCase: AddLogUseCase,
    private val bulkOrderUseCase: BulkOrderUseCase,
) : ViewModel() {

    var state by mutableStateOf(BulkOrderState())
    var fullName by mutableStateOf("")
    var email by mutableStateOf("")
    var mobile by mutableStateOf("")
    var address by mutableStateOf("")
    var city by mutableStateOf("")
    var message by mutableStateOf("")

    init {
        getCityList()
    }

    fun onEvent(event: BulkOrderEvents) {
        when (event) {
            is BulkOrderEvents.Save -> {
                if (validateForm())
                    save()
                else
                    state = state.copy(isError = true, message = "Fill all Fields")
            }

            is BulkOrderEvents.UpdateState -> {
                state = state.copy(isError = false, message = null)
            }
            is BulkOrderEvents.AddLog -> {
                addLog(event.logRequest)
            }
        }
    }


    private fun addLog(logRequest: LogRequest) {
        viewModelScope.launch {
            addLogUseCase.execute(logRequest)
        }
    }


    private fun getCityList() {
        viewModelScope.launch {
            cityListUseCase.execute().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        val data = result.data!!
                        val isError = data.status == Status.error.name || data.result.isEmpty()

                        state = state.copy(
                            isLoading = false,
                            isError = isError,
                            cities = data.result
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

    private fun validateForm(): Boolean {
        return fullName.isNotEmpty() &&
                email.isNotEmpty() &&
                mobile.isNotEmpty() &&
                address.isNotEmpty() &&
                city.isNotEmpty() &&
                message.isNotEmpty()
    }

    private fun save() {
        viewModelScope.launch {
            bulkOrderUseCase.execute(
                fullName, email, mobile, city, address, message
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        val data = result.data!!
                        val isError = data.status == Status.error.name
                        if (!isError) {
                            addLog(
                                LogRequest(
                                    type = LogType.actionPerform,
                                    event = "bulk order conirmed",
                                    page_name = "/bulk order",
                                )
                            )
                        }

                        state = state.copy(
                            isLoading = false,
                            isError = isError,
                            createOrder = data
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


}