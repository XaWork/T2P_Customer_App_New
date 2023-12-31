package me.taste2plate.app.customer.presentation.screens.contact_us

import me.taste2plate.app.customer.domain.model.SettingsModel
import me.taste2plate.app.customer.domain.model.auth.User
import me.taste2plate.app.customer.domain.model.user.CommonResponse
import me.taste2plate.app.customer.domain.model.user.DeleteFromWishlistModel
import me.taste2plate.app.customer.domain.model.user.WishListModel

data class ContactUsState(
    val isLoading:Boolean = false,
    val isError: Boolean = false,
    val message: String? = null,
    val setting: SettingsModel.Result? = null
)
