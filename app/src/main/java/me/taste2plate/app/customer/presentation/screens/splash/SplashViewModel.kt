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
import me.taste2plate.app.customer.domain.model.SettingsModel
import me.taste2plate.app.customer.domain.use_case.SettingsUseCase
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val settingsUseCase: SettingsUseCase,
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
            state = state.copy(isLogin = userPref.isLogin())
        }
    }

    private fun getSettings() {
        viewModelScope.launch {
            settingsUseCase.settings().collect { result ->
                state = when (result) {
                    is Resource.Loading -> {
                        state.copy(loading = true)
                    }

                    is Resource.Success -> {
                        saveSetting(result.data!!)
                        state.copy(loading = false)
                    }

                    is Resource.Error -> {
                        state.copy(error = result.message, loading = false)
                    }
                }
            }

            /*customRepo.setting().collect{
                Log.e("Result", it.data.toString())
            }*/
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