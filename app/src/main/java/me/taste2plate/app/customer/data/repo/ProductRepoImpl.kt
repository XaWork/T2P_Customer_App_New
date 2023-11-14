package me.taste2plate.app.customer.data.repo

import me.taste2plate.app.customer.data.api.ProductApi
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

    override suspend fun productByBrand(id: String, taste: String): ProductListModel {
        return api.productsByBrand(id, taste)
    }

    override suspend fun productByCuisine(id: String, taste: String): ProductListModel {
        return api.productsByCuisine(id, taste)
    }

    override suspend fun productByCategory(id: String, taste: String): ProductListModel {
        return api.productBySubcategory(id, taste)
    }

    override suspend fun productDetails(id: String): ProductDetailsModel {
        return api.productDetails(id)
    }
}