package me.taste2plate.app.customer.presentation.screens.wishlist

import me.taste2plate.app.customer.domain.model.custom.LogRequest

sealed class WishlistEvents {
    object GetWishlist : WishlistEvents()

    object UpdateState: WishlistEvents()



    data class AddLog(
        val logRequest: LogRequest
    ) : WishlistEvents()

    data class RemoveFromWishlist(val productId: String) : WishlistEvents()
}
