package me.taste2plate.app.customer.domain.repo

import me.taste2plate.app.customer.domain.model.product.ProductListModel

interface ProductRepo {
    suspend fun productByCity(
        id: String,
        taste: String
    ): ProductListModel

    suspend fun productByBrand(
        id: String,
        taste: String
    ): ProductListModel

    suspend fun productByCuisine(
        id: String,
        taste: String
    ): ProductListModel
}