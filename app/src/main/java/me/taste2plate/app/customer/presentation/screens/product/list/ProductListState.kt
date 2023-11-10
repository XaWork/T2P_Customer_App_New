package me.taste2plate.app.customer.presentation.screens.product.list

import me.taste2plate.app.customer.domain.model.SettingsModel
import me.taste2plate.app.customer.domain.model.product.ProductListModel
import me.taste2plate.app.customer.domain.model.user.CommonResponse
import me.taste2plate.app.customer.domain.model.user.DeleteFromWishlistModel
import me.taste2plate.app.customer.domain.model.user.WishListModel

data class ProductListState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val message: String? = null,
    val productList: List<ProductListModel.Result> = emptyList()
)