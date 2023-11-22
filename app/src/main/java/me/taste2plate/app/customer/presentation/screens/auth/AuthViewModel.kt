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
import me.taste2plate.app.customer.domain.model.auth.User
import me.taste2plate.app.customer.domain.use_case.auth.LoginUseCase
import me.taste2plate.app.customer.domain.use_case.auth.VerifyOTPUseCase
import me.taste2plate.app.customer.domain.use_case.user.EditProfileUseCase
import me.taste2plate.app.customer.domain.use_case.user.GetProfileUseCase
import me.taste2plate.app.customer.utils.Constants
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val verifyOTPUseCase: VerifyOTPUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val editProfileUseCase: EditProfileUseCase,
    private val userPref: UserPref
) : ViewModel() {

    var state by mutableStateOf(AuthState())

    var mobile by mutableStateOf("")
    var fullName by mutableStateOf("")
    var email by mutableStateOf("")
    var otp by mutableStateOf("")
    val mobileLength = 10
    private val otpLength = 6

    fun onEvent(event: AuthEvents) {
        when (event) {
            AuthEvents.Login -> {
                validateMobile()
            }

            AuthEvents.VerifyOTP -> {
                validateOTP()
            }

            AuthEvents.GetUser -> {
                getUser()
            }

            AuthEvents.ResendOTP -> {
                login()
            }

            AuthEvents.UpdateState -> {
                state = state.copy(loading = false, isError = false, message = null)
            }

            AuthEvents.SignUp -> {
                if (signUpFormValidate() && state.user != null)
                    editProfile()
                else
                    state = state.copy(loading = false, isError = true, message = "Fill all data")
            }
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            state = state.copy(user = userPref.getUser())
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
                                isError = false,
                                message = ""
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
                        state = if (result.data?.status == Status.success.name) {
                            saveUser(result.data.data)
                            // delay(1000)
                            state.copy(
                                loading = false,
                                verifyOTPModel = result.data,
                            )
                        } else
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


    /** ------------------- Signup  ------------------------------------- **/
    private fun signUpFormValidate(): Boolean {
        return fullName.isNotEmpty() && email.isNotEmpty()
    }

    private fun editProfile() {
        val mobile = state.user!!.mobile
        viewModelScope.launch {
            editProfileUseCase.execute(
                fullName, mobile, email
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(loading = true)
                    }

                    is Resource.Success -> {
                        state = if (result.data?.status == Status.success.name) {
                            getProfile()
                            state.copy(
                                loading = false,
                            )
                        } else
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

    private fun getProfile() {
        viewModelScope.launch {
            getProfileUseCase.execute().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        // state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        val isError = result.data?.status == Status.error.name
                        state = state.copy(
                            loading = false,
                            isError = isError,
                        )

                        if (!isError)
                            saveUser(result.data!!.result, registerSuccess = true)
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

    private fun saveUser(user: User, registerSuccess: Boolean = false) {
        viewModelScope.launch {
            userPref.saveUser(user)
        }
        state = state.copy(loginSuccess = true, registerSuccess = registerSuccess)
    }

}