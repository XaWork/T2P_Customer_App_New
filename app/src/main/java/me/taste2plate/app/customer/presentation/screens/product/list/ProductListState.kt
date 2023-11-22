package me.taste2plate.app.customer.presentation.screens.product.list

import me.taste2plate.app.customer.domain.mapper.CommonForItem
import me.taste2plate.app.customer.domain.model.SettingsModel
import me.taste2plate.app.customer.domain.model.product.ProductDetailsModel
import me.taste2plate.app.customer.domain.model.product.ProductListModel
import me.taste2plate.app.customer.domain.model.user.CartModel
import me.taste2plate.app.customer.domain.model.user.CommonResponse
import me.taste2plate.app.customer.domain.model.user.DeleteFromWishlistModel
import me.taste2plate.app.customer.domain.model.user.WishListModel

data class ProductListState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val cartData: CartModel? = null,
    val message: String? = null,
    val itemInfo: CommonForItem? = null,
    val checked: Boolean = false,
    val productList: List<ProductListModel.Result> = emptyList(),
    val productDetails: ProductDetailsModel? = null
)
