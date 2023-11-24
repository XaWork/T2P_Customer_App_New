package me.taste2plate.app.customer.domain.repo

import me.taste2plate.app.customer.domain.model.CouponModel
import me.taste2plate.app.customer.domain.model.custom.CheckAvailabilityModel
import me.taste2plate.app.customer.domain.model.product.ProductBySliderModel
import me.taste2plate.app.customer.domain.model.product.ProductDetailsModel
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

    suspend fun productByCategory(
        id: String,
        taste: String
    ): ProductListModel

    suspend fun productByQuery(
        query: String,
    ): ProductListModel

    suspend fun productsBySlider(
        name: String,
        taste: String
    ): ProductBySliderModel

    suspend fun productDetails(
        id: String,
    ): ProductDetailsModel

    suspend fun getOfferByCity(
        id: String,
    ): CouponModel

    suspend fun checkAvailability(
        zipCode: String,
        vendorId: String,
    ): CheckAvailabilityModel
}