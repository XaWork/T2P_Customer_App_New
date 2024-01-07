package me.taste2plate.app.customer.data.repo

import me.taste2plate.app.customer.data.api.ProductApi
import me.taste2plate.app.customer.domain.model.CouponModel
import me.taste2plate.app.customer.domain.model.custom.CheckAvailabilityModel
import me.taste2plate.app.customer.domain.model.product.CalculateCheckoutDistanceModel
import me.taste2plate.app.customer.domain.model.product.CutOffTimeCheckModel
import me.taste2plate.app.customer.domain.model.product.ProductBySliderModel
import me.taste2plate.app.customer.domain.model.product.ProductDetailsModel
import me.taste2plate.app.customer.domain.model.product.ProductListModel
import me.taste2plate.app.customer.domain.repo.ProductRepo
import javax.inject.Inject

class ProductRepoImpl @Inject constructor(
    private val api: ProductApi
) : ProductRepo {
    override suspend fun productByCity(id: String, taste: String): ProductListModel {
        return api.productsByCity(id, taste)
    }

    override suspend fun calculateCheckoutDistance(
        addressId: String,
        productId: String
    ): CalculateCheckoutDistanceModel {
        return api.calculateCheckoutDistance(addressId, productId)
    }

    override suspend fun productByBrand(id: String, taste: String): ProductListModel {
        return api.productsByBrand(id, taste)
    }

    override suspend fun productByCuisine(id: String, taste: String): ProductListModel {
        return api.productsByCuisine(id, taste)
    }

    override suspend fun productByCategory(id: String, taste: String): ProductListModel {
        return api.productBySubcategory(id, taste)
    }

    override suspend fun productByQuery(query: String): ProductListModel {
        return api.productByQuery(query)
    }

    override suspend fun productsBySlider(name: String, taste: String): ProductListModel {
        return api.productsBySlider(name, taste)
    }

    override suspend fun productDetails(
        id: String,
        lat: String?,
        lng: String?,
        city: String?,
    ): ProductDetailsModel {
        return api.productDetails(id, lat, lng, city)
    }

    override suspend fun getOfferByCity(id: String): CouponModel {
        return api.getOfferByCity(id)
    }

    override suspend fun checkAvailability(
        zipCode: String,
        vendorId: String
    ): CheckAvailabilityModel {
        return api.checkAvailability(zipCode, vendorId)
    }

    override suspend fun checkCutOffTimeCheck(
        startCity: String,
        endCity: String
    ): CutOffTimeCheckModel {
        return api.checkCutOffTimeCheck(startCity, endCity)
    }
}