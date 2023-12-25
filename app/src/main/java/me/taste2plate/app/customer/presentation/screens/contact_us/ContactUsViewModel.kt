package me.taste2plate.app.customer.presentation.screens.contact_us

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.taste2plate.app.customer.T2PApp
import me.taste2plate.app.customer.data.Resource
import me.taste2plate.app.customer.data.Status
import me.taste2plate.app.customer.data.UserPref
import me.taste2plate.app.customer.domain.model.custom.LogRequest
import me.taste2plate.app.customer.domain.model.user.DeleteFromWishlistModel
import me.taste2plate.app.customer.domain.use_case.analytics.AddLogUseCase
import me.taste2plate.app.customer.domain.use_case.user.wishlist.RemoveFromWishlistUseCase
import me.taste2plate.app.customer.domain.use_case.user.wishlist.WishlistUseCase
import me.taste2plate.app.customer.presentation.screens.profile.ProfileEvents
import me.taste2plate.app.customer.presentation.screens.profile.ProfileState
import me.taste2plate.app.customer.presentation.screens.wishlist.WishlistEvents
import me.taste2plate.app.customer.presentation.screens.wishlist.WishlistState
import javax.inject.Inject

@HiltViewModel
class ContactUsViewModel @Inject constructor(
    private val userPref: UserPref,
    private val addLogUseCase: AddLogUseCase,
) : ViewModel() {

    var state by mutableStateOf(ContactUsState())

    init {
        getSettings()
    }
     fun addLog(logRequest: LogRequest) {
        viewModelScope.launch {
            addLogUseCase.execute(logRequest)
        }
    }

    private fun getSettings() {
        viewModelScope.launch {
            state = state.copy(setting = userPref.getSettings())
        }
    }

}