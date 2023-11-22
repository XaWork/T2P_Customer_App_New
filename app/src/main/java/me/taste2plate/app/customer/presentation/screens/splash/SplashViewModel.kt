package me.taste2plate.app.customer.presentation.screens.splash

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
import me.taste2plate.app.customer.domain.model.SettingsModel
import me.taste2plate.app.customer.domain.use_case.SettingsUseCase
import me.taste2plate.app.customer.domain.use_case.user.address.AllAddressUseCase
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val settingsUseCase: SettingsUseCase,
    private val allAddressUseCase: AllAddressUseCase,
    private val userPref: UserPref
) : ViewModel() {

    var state by mutableStateOf(SplashState())

    init {
        getSettings()
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
                state = state.copy(user = userPref.getUser())
                getAddress()
            }
    }

    private fun getSettings() {
        viewModelScope.launch {
            settingsUseCase.settings().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(loading = true)
                    }

                    is Resource.Success -> {
                        if (result.data != null && result.data.status == Status.success.name) {
                            saveSetting(result.data)
                        }
                    }

                    is Resource.Error -> {
                        state = state.copy(error = result.message, loading = false)
                    }
                }
            }

            /*customRepo.setting().collect{
                Log.e("Result", it.data.toString())
            }*/
        }
    }

    private fun getAddress() {
        if (state.isLogin && state.user != null)
            viewModelScope.launch {
                allAddressUseCase.execute(
                ).collect { result ->
                    when (result) {
                        is Resource.Loading -> {}

                        is Resource.Success -> {
                            val isError = result.data?.status == Status.error.name
                            state =
                                state.copy(
                                    loading = false,
                                    isError = isError,
                                    error = if (isError) result.data?.message else "",
                                    addressListModel = result.data
                                )
                        }

                        is Resource.Error -> {
                            state = state.copy(
                                loading = false,
                                isError = true,
                                error = result.message
                            )
                        }
                    }

                }
            }
    }

    private fun saveSetting(setting: SettingsModel) {
        viewModelScope.launch {
            userPref.saveSettings(setting.result)
            state = state.copy(settings = setting)
            isUserLogin()
        }
    }

}