package me.taste2plate.app.customer.presentation.screens.wallet

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.taste2plate.app.customer.data.Resource
import me.taste2plate.app.customer.data.Status
import me.taste2plate.app.customer.domain.use_case.user.MyPlanUseCase
import me.taste2plate.app.customer.domain.use_case.user.WalletTransactionUseCase
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val myPlanUseCase: MyPlanUseCase,
    private val walletTransactionUseCase: WalletTransactionUseCase
) : ViewModel() {

    var state by mutableStateOf(WalletState())


    init {
        onEvent(WalletEvents.GetWalletData)
    }

    fun onEvent(event: WalletEvents) {
        when (event) {
            is WalletEvents.GetWalletData -> {
                getWalletData()
            }

            is WalletEvents.GetWalletTransaction -> {
                getWalletTransaction()
            }
        }
    }

    private fun getWalletData() {
        viewModelScope.launch {
            myPlanUseCase.execute().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        val isError = result.data == null
                        state = state.copy(
                            isLoading = false,
                            isError = isError,
                            message = null,
                            myPlan = result.data
                        )
                        onEvent(WalletEvents.GetWalletTransaction)
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

    private fun getWalletTransaction() {
        viewModelScope.launch {
            walletTransactionUseCase.execute().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        val data = result.data
                        val isError = data?.status == Status.error.name
                        Log.e("Data", result.data.toString())
                        state = state.copy(
                            isLoading = false,
                            isError = isError,
                            message = null,
                            transactions = if (isError || data == null || data.result.isEmpty()) emptyList()
                            else data.result
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