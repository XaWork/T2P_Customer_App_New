package me.taste2plate.app.customer.presentation.screens.product.list

import me.taste2plate.app.customer.domain.mapper.CommonForItem
import me.taste2plate.app.customer.domain.model.SettingsModel
import me.taste2plate.app.customer.domain.model.custom.CheckAvailabilityModel
import me.taste2plate.app.customer.domain.model.product.ProductDetailsModel
import me.taste2plate.app.customer.domain.model.product.ProductListModel
import me.taste2plate.app.customer.domain.model.user.CartModel
import me.taste2plate.app.customer.domain.model.user.CommonResponse
import me.taste2plate.app.customer.domain.model.user.WishListModel
import me.taste2plate.app.customer.domain.model.user.address.AddressListModel
import me.taste2plate.app.customer.presentation.screens.home.FoodItemUpdateInfo

data class ProductListState(
    val isLoading: Boolean = false,
    val reviewLoading: Boolean = false,
    val buttonLoading: Boolean = false,
    val addToCartEnable: Boolean = true,
    val isError: Boolean = false,
    val cartError: Boolean = false,
    val cartData: CartModel? = null,
    val message: String? = null,
    val defaultAddress: AddressListModel.Result? = null,
    val foodItemUpdateInfo: FoodItemUpdateInfo? = null,
    val checkAvailabilityModel: CheckAvailabilityModel? = null,
    val addToWishlistResponse: CommonResponse? = null,
    val postReviewResponse: CommonResponse? = null,
    val itemInfo: CommonForItem? = null,
    val checked: Boolean = false,
    val wishListData: WishListModel? = null,
    val settings: SettingsModel.Result? = null,
    val productList: List<ProductListModel.Result> = emptyList(),
    val productDetails: ProductDetailsModel? = null
)
