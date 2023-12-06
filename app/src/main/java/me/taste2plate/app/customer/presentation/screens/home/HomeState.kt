package me.taste2plate.app.customer.presentation.screens.home

import me.taste2plate.app.customer.domain.model.HomeModel
import me.taste2plate.app.customer.domain.model.SettingsModel
import me.taste2plate.app.customer.domain.model.auth.User
import me.taste2plate.app.customer.domain.model.user.CartModel
import me.taste2plate.app.customer.domain.model.user.CommonResponse
import me.taste2plate.app.customer.domain.model.user.DeleteFromWishlistModel
import me.taste2plate.app.customer.domain.model.user.WishListModel
import me.taste2plate.app.customer.domain.model.user.address.AddressListModel

data class HomeState(
    val isLoading: Boolean = false,
    val cartError: Boolean = false,
    val addressLoader: Boolean = false,
    val checked: Boolean = false,
    val user: User? = null,
    val setting: SettingsModel.Result? = null,
    val errorMessage: String? = null,
    val message: String? = null,
    val isError: Boolean = false,
    val homeData: HomeModel? = null,
    val wishListData: WishListModel? = null,
    val deleteFromWishlistModel: DeleteFromWishlistModel? = null,
    val cartData: CartModel? = null,
    val addressListModel: AddressListModel? = null,
    val addToWishlistResponse: CommonResponse? = null,
    val addToCartResponse: CommonResponse? = null,
    val foodItemUpdateInfo: FoodItemUpdateInfo? = null,
    val defaultAddress: AddressListModel.Result? = null,
)

data class FoodItemUpdateInfo(
    val id : String,
    val isLoading: Boolean = false,
    val wishlistItem: Boolean = true,
    val added: Boolean = false,
)
