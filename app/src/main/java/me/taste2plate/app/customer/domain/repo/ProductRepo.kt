package me.taste2plate.app.customer.domain.repo

import me.taste2plate.app.customer.domain.model.product.ProductListModel

interface ProductRepo {
    suspend fun productByCity(
        cityId: String,
        taste: String
    ): ProductListModel
}