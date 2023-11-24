package me.taste2plate.app.customer.presentation.screens.product.list

import me.taste2plate.app.customer.domain.mapper.CommonForItem
import me.taste2plate.app.customer.presentation.screens.home.HomeEvent

sealed class ProductEvents {
    data class GetProducts(val itemInfo: CommonForItem) : ProductEvents()
    object ChangeTaste: ProductEvents()
    object GetProductDetails: ProductEvents()
    object UpdateState: ProductEvents()

    data class AddToWishlist(val productId: String): ProductEvents()
    data class CheckAvailibility(val zip: String): ProductEvents()
    data class PostReview(val rating: Float, val review: String): ProductEvents()

    data class UpdateCart(
        val quantity: Int,
        val productId: String,
    ) : ProductEvents()
}
