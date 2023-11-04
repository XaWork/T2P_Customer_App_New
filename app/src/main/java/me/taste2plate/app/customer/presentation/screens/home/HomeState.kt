package me.taste2plate.app.customer.presentation.screens.home

import me.taste2plate.app.customer.domain.model.HomeModel
import me.taste2plate.app.customer.domain.model.user.CartModel
import me.taste2plate.app.customer.domain.model.user.CommonResponse
import me.taste2plate.app.customer.domain.model.user.WishListModel

data class HomeState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val message: String? = null,
    val isError: Boolean = false,
    val homeData: HomeModel? = null,
    val wishListData: WishListModel? = null,
    val cartData: CartModel? = null,
    val addToWishlistResponse: CommonResponse? = null,
    val foodItemUpdateInfo: FoodItemUpdateInfo? = null,
)

data class FoodItemUpdateInfo(
    val id : String,
    val isLoading: Boolean = false,
    val added: Boolean = false,
)
