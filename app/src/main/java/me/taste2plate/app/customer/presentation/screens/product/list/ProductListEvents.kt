package me.taste2plate.app.customer.presentation.screens.product.list

import me.taste2plate.app.customer.domain.mapper.CommonForItem

sealed class ProductListEvents {
    data class GetProducts(val itemInfo: CommonForItem) : ProductListEvents()
}
