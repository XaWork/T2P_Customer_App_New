package me.taste2plate.app.customer.presentation.screens.profile

import me.taste2plate.app.customer.domain.model.SettingsModel
import me.taste2plate.app.customer.domain.model.auth.User
import me.taste2plate.app.customer.domain.model.user.CommonResponse
import me.taste2plate.app.customer.domain.model.user.DeleteFromWishlistModel
import me.taste2plate.app.customer.domain.model.user.WishListModel

data class ProfileState(
    val isLoading:Boolean = false,
    val userUpdate:Boolean = false,
    val isError: Boolean = false,
    val userDeleted: Boolean = false,
    val message: String? = null,
    val user: User? = null,
    val editProfileResponse: CommonResponse? = null
)
