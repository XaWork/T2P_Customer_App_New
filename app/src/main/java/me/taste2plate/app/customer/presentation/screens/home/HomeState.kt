package me.taste2plate.app.customer.presentation.screens.home

import android.location.Location
import me.taste2plate.app.customer.domain.model.HomeModel
import me.taste2plate.app.customer.domain.model.user.CartModel
import me.taste2plate.app.customer.domain.model.user.WishListModel

data class HomeState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isError: Boolean = false,
    val homeData: HomeModel? = null,
    val wishListData: WishListModel? = null,
    val cartData: CartModel? = null,
)
