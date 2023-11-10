package me.taste2plate.app.customer.data.api

import me.taste2plate.app.customer.domain.model.product.ProductListModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApi {

    @GET("all-products")
    suspend fun productsByCity(
        @Query("city") cityId: String,
        @Query("taste") taste: String
    ): ProductListModel
}