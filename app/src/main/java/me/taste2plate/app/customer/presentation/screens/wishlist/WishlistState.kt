package me.taste2plate.app.customer.presentation.screens.wishlist

import me.taste2plate.app.customer.domain.model.SettingsModel
import me.taste2plate.app.customer.domain.model.user.CommonResponse
import me.taste2plate.app.customer.domain.model.user.DeleteFromWishlistModel
import me.taste2plate.app.customer.domain.model.user.WishListModel

data class WishlistState(
    val isLoading:Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val normalMessage: String? = null,
    val wishlist: WishListModel? = null,
    val addToWishlistResponse: CommonResponse? = null,
    val deleteFromWishlistModel: DeleteFromWishlistModel? = null
)
