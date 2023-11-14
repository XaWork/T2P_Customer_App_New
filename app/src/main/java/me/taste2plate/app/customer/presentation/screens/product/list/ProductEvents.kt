package me.taste2plate.app.customer.presentation.screens.product.list

import me.taste2plate.app.customer.domain.mapper.CommonForItem

sealed class ProductEvents {
    data class GetProducts(val itemInfo: CommonForItem) : ProductEvents()
    object ChangeTaste: ProductEvents()
    object GetProductDetails: ProductEvents()
}
