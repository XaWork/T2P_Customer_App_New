package me.taste2plate.app.customer.presentation.screens.profile

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
import me.taste2plate.app.customer.domain.use_case.user.EditProfileUseCase
import me.taste2plate.app.customer.domain.use_case.user.GetProfileUseCase
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userPref: UserPref,
    private val editProfileUseCase: EditProfileUseCase,
    private val getProfileUseCase: GetProfileUseCase
) : ViewModel() {

    var state by mutableStateOf(ProfileState())
    var fullName by mutableStateOf("")
    var email by mutableStateOf("")
    var mobile by mutableStateOf("")


    init {
        getUser()
    }

    fun onEvent(event: ProfileEvents) {
        when (event) {
            ProfileEvents.EditProfile -> {
                if (validateForm())
                    editProfile()
                else
                    state = state.copy(
                        isError = true,
                        message = "Fill all fields"
                    )
            }
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            state = state.copy(user = userPref.getUser())
            setData()
        }
    }

    private fun setData() {
        val user = state.user
        fullName = user!!.fullName
        email = user.email!!
        mobile = user.mobile
    }

    private fun validateForm(): Boolean {
        return fullName.isNotEmpty() && mobile.isNotEmpty()
    }

    private fun editProfile() {
        viewModelScope.launch {
            editProfileUseCase.execute(fullName, mobile, email).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        val isError = result.data?.status == Status.error.name
                        state = state.copy(
                            isError = isError,
                            message = result.data?.message,
                            editProfileResponse = result.data
                        )

                        if (!isError)
                            getProfile()
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
                            isLoading = false,
                            isError = isError,
                           // message = result.data?.message,
                        )

                        if (!isError)
                            userPref.saveUser(result.data!!.result)
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