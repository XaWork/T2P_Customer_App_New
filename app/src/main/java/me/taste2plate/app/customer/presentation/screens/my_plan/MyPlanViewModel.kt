package me.taste2plate.app.customer.presentation.screens.my_plan

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
import me.taste2plate.app.customer.domain.use_case.user.MyPlanUseCase
import me.taste2plate.app.customer.presentation.screens.membership_plan.MembershipState
import javax.inject.Inject

@HiltViewModel
class MyPlanViewModel @Inject constructor(
    private val myPlanUseCase: MyPlanUseCase,
    private val addLogUseCase: AddLogUseCase,
) : ViewModel() {

    var state by mutableStateOf(MyPlanState())


    init {
        getMyPlan()
    }
      fun addLog(logRequest: LogRequest) {
        viewModelScope.launch {
            addLogUseCase.execute(logRequest)
        }
    }

    private fun getMyPlan() {
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