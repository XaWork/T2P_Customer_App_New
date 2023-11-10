package me.taste2plate.app.customer.data.repo

import me.taste2plate.app.customer.data.api.ProductApi
import me.taste2plate.app.customer.domain.model.product.ProductListModel
import me.taste2plate.app.customer.domain.repo.ProductRepo
import javax.inject.Inject

class ProductRepoImpl @Inject constructor(
    private val api: ProductApi
) : ProductRepo {
    override suspend fun productByCity(cityId: String, taste: String): ProductListModel {
        return api.productsByCity(cityId, taste)
    }
}