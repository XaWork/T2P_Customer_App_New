package me.taste2plate.app.customer.presentation.screens.cart

sealed class CheckoutEvents {
    object GetCart : CheckoutEvents()
    object UpdateState : CheckoutEvents()
    data class UpdateCart(
        val productId: String,
        val quantity: Int
    ) : CheckoutEvents()
}
