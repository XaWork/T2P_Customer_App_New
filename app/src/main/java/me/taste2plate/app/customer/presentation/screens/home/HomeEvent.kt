package me.taste2plate.app.customer.presentation.screens.home

import android.content.Context

sealed class HomeEvent{
    object GetHome : HomeEvent()
    data class AddToWishlist(
        val productId: String,
    ) : HomeEvent()
    data class UpdateState(
        val changeAddToWishlistResponse: Boolean = false,
    ) : HomeEvent()
}
