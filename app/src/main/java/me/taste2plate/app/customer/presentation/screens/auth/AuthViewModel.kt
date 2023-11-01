package me.taste2plate.app.customer.presentation.screens.auth


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
import me.taste2plate.app.customer.domain.use_case.auth.LoginUseCase
import me.taste2plate.app.customer.domain.use_case.auth.VerifyOTPUseCase
import me.taste2plate.app.customer.utils.Constants
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val verifyOTPUseCase: VerifyOTPUseCase,
    private val userPref: UserPref
) : ViewModel() {

    var state by mutableStateOf(AuthState())

    var mobile by mutableStateOf("")
    var otp by mutableStateOf("")
    val mobileLength = 10
    val otpLength = 6

    fun onEvent(event: AuthEvents) {
        when (event) {
            AuthEvents.Login -> {
                validateMobile()
            }
            AuthEvents.VerifyOTP -> {
                validateOTP()
            }
            AuthEvents.ResendOTP -> {
                login()
            }
        }
    }

    /** ------------------- Login ------------------------------------- **/
    private fun validateMobile() {
        if (mobile.length < mobileLength)
            state = state.copy(
                isError = true,
                message = "Mobile must be at least 10 characters",
            )
        else
            login()
    }

    private fun login() {
        viewModelScope.launch {
            loginUseCase.login(
                mobile = mobile,
                token = userPref.getToken(),
                device = Constants.deviceType
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(loading = true)
                    }

                    is Resource.Success -> {
                        state = if (result.data?.status == Status.success.name)
                            state.copy(
                                loading = false,
                                loginModel = result.data,
                                message = result.data.message,
                                isError = true,
                            )
                        else
                            state.copy(
                                loading = false,
                                message = result.data?.message,
                                isError = true
                            )
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            loading = false,
                            isError = true,
                            message = result.message
                        )
                    }
                }
            }
        }
    }


    /** ------------------- OTP ------------------------------------- **/
    private fun validateOTP() {
        if (otp.length < otpLength)
            state = state.copy(
                isError = true,
                message = "OTP must be at least $otpLength characters",
            )
        else
            verifyOTP()
    }

    private fun verifyOTP() {
        viewModelScope.launch {
            verifyOTPUseCase.execute(
                mobile = mobile,
                token = userPref.getToken(),
                otp = otp
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(loading = true)
                    }

                    is Resource.Success -> {
                        state = if (result.data?.status == Status.success.name)
                            state.copy(
                                loading = false,
                                verifyOTPModel = result.data,
                                message = result.data.message,
                                isError = false,
                            )
                        else
                            state.copy(
                                loading = false,
                                message = result.data?.message,
                                isError = true
                            )
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            loading = false,
                            isError = true,
                            message = result.message
                        )
                    }
                }
            }
        }
    }

}