package me.taste2plate.app.customer.presentation.screens.membership_plan

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
import me.taste2plate.app.customer.domain.use_case.analytics.AddLogUseCase
import me.taste2plate.app.customer.domain.use_case.custom.AllPlanUseCase
import javax.inject.Inject

@HiltViewModel
class MembershipPlanViewModel @Inject constructor(
    private val allPlanUseCase: AllPlanUseCase,
    private val addLogUseCase: AddLogUseCase,
) : ViewModel() {

    var state by mutableStateOf(MembershipState())


    init {
        getPlans()
    }


    fun addLog(logRequest: LogRequest) {
        viewModelScope.launch {
            addLogUseCase.execute(logRequest)
        }
    }

    private fun getPlans() {
        viewModelScope.launch {
            allPlanUseCase.execute().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        val isError = result.data?.status == Status.error.name
                        val plans = result.data?.plans
                        state = state.copy(
                            isLoading = false,
                            isError = isError,
                            message = null,
                            plans = if (result.data == null || plans!!.isEmpty()) emptyList() else plans
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