package me.taste2plate.app.customer.presentation.screens.wishlist

sealed class WishlistEvents {
    object GetWishlist : WishlistEvents()

    object UpdateState: WishlistEvents()

    data class RemoveFromWishlist(val productId: String) : WishlistEvents()
}
