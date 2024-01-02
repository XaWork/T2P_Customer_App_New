package me.taste2plate.app.customer.presentation.screens.ghar_ka_khana

import me.taste2plate.app.customer.domain.model.user.address.AddressListModel


sealed class GharKaKhanaEvent {
    data class SetPDLocation(
        val location: AddressListModel.Result,
        val pdLocationType: PDLocation
    ) : GharKaKhanaEvent()

    object GetAddress: GharKaKhanaEvent()

    object CheckCutOffTime: GharKaKhanaEvent()
    object UpdateState: GharKaKhanaEvent()
    object AddToCart: GharKaKhanaEvent()
    object BookNow: GharKaKhanaEvent()
    data class DeleteCart(
        val id: String
    ): GharKaKhanaEvent()
}