package me.taste2plate.app.customer.presentation.screens.splash

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.taste2plate.app.customer.data.Resource
import me.taste2plate.app.customer.data.UserPref
import me.taste2plate.app.customer.domain.model.custom.LogRequest
import me.taste2plate.app.customer.domain.model.custom.LogType
import me.taste2plate.app.customer.domain.use_case.analytics.AddLogUseCase
import me.taste2plate.app.customer.domain.use_case.analytics.GeoIpUseCase
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val geoIpUseCase: GeoIpUseCase,
    private val addLogUseCase: AddLogUseCase,
    private val userPref: UserPref
) : ViewModel() {

    var state by mutableStateOf(SplashState())

    init {
        checkIp()
    }

    fun onEvent(event: SplashEvents) {
        when (event) {
            SplashEvents.GetSettings -> {
                //  getSettings()
            }

            SplashEvents.IsUserLogin -> {
                isUserLogin()
            }
        }
    }

    private fun checkIp() {
        state = state.copy(loading = true)
        viewModelScope.launch {
            val ip = userPref.getIp()
            if (ip.isEmpty()) {
                getIpAddress()
            } else {
                addLog()
            }
        }
    }

    private fun getIpAddress() {
        viewModelScope.launch {
            geoIpUseCase.execute().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        //state = state.copy(loading = true)
                    }

                    is Resource.Success -> {
                        userPref.saveIp(result.data!!.ip)
                        addLog()
                    }

                    is Resource.Error -> {
                        state = state.copy(error = result.message, loading = false)
                    }
                }
            }
        }
    }

    private fun addLog() {
        viewModelScope.launch {
            addLogUseCase.execute(
                LogRequest(
                    type = LogType.pageVisit,
                    event = "enter in splash screen",
                    page_name = "/splash",
                )
            )
        }
        isUserLogin()
    }

    private fun isUserLogin() {
        viewModelScope.launch {
            val isLogin = userPref.isLogin()
            state = state.copy(isLogin = isLogin, loading = isLogin)
            getUser()
        }
    }

    private fun getUser() {
        if (state.isLogin)
            viewModelScope.launch {
                state = state.copy(user = userPref.getUser(), loading = false)
                //getAddress()
            }
    }


}