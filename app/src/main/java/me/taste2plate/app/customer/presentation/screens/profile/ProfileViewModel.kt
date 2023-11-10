package me.taste2plate.app.customer.presentation.screens.profile

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
import me.taste2plate.app.customer.domain.model.user.DeleteFromWishlistModel
import me.taste2plate.app.customer.domain.use_case.user.wishlist.RemoveFromWishlistUseCase
import me.taste2plate.app.customer.domain.use_case.user.wishlist.WishlistUseCase
import me.taste2plate.app.customer.presentation.screens.wishlist.WishlistEvents
import me.taste2plate.app.customer.presentation.screens.wishlist.WishlistState
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userPref: UserPref
) : ViewModel() {

    var state by mutableStateOf(ProfileState())

    init {
        getUser()
    }

    fun onEvent(event: ProfileEvents) {

    }

    private fun getUser() {
        viewModelScope.launch {
            state = state.copy(user = userPref.getUser())
        }
    }

}