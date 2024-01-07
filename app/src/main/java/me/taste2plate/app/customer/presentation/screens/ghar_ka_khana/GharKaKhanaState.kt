package me.taste2plate.app.customer.presentation.screens.ghar_ka_khana

import me.taste2plate.app.customer.domain.model.SettingsModel
import me.taste2plate.app.customer.domain.model.auth.User
import me.taste2plate.app.customer.domain.model.custom.GharKaKhanaCategoryModel
import me.taste2plate.app.customer.domain.model.custom.GharKaKhanaSubCategoryModel
import me.taste2plate.app.customer.domain.model.product.CutOffTimeCheckModel
import me.taste2plate.app.customer.domain.model.user.CommonResponse
import me.taste2plate.app.customer.domain.model.user.GharKaKhanaCheckoutModel
import me.taste2plate.app.customer.domain.model.user.GharKaKhanaFetchCartModel
import me.taste2plate.app.customer.domain.model.user.address.AddressListModel
import me.taste2plate.app.customer.presentation.screens.checkout.DeliveryType

data class GharKaKhanaState(
    var addressLoader: Boolean = false,
    var isLoading: Boolean = false,
    var buttonLoader: Boolean = false,
    var bookButtonLoader: Boolean = false,
    var isError : Boolean = false,
    var finish : Boolean = false,
    var moveToCheckout : Boolean = false,
    var user : User? = null,
    var message : String? = null,
    var pickupLocation: AddressListModel.Result? = null,
    var checkout: GharKaKhanaCheckoutModel? = null,
    var confirmCheckout: CommonResponse? = null,
    var cutOffTimeCheckModel: CutOffTimeCheckModel? = null,
    var destinationLocation: AddressListModel.Result? = null,
    var category: List<GharKaKhanaCategoryModel.Result> = emptyList(),
    var subCategory: List<GharKaKhanaSubCategoryModel.Result> = emptyList(),
    val addressListModel: List<AddressListModel.Result> = emptyList(),
    val cartItems: List<GharKaKhanaFetchCartModel.Result> = emptyList(),
)