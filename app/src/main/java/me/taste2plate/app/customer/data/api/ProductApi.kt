package me.taste2plate.app.customer.data.api

import me.taste2plate.app.customer.domain.model.CouponModel
import me.taste2plate.app.customer.domain.model.custom.CheckAvailabilityModel
import me.taste2plate.app.customer.domain.model.product.CalculateCheckoutDistanceModel
import me.taste2plate.app.customer.domain.model.product.CutOffTimeCheckModel
import me.taste2plate.app.customer.domain.model.product.ProductBySliderModel
import me.taste2plate.app.customer.domain.model.product.ProductDetailsModel
import me.taste2plate.app.customer.domain.model.product.ProductListModel
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProductApi {

    @GET("all-products")
    suspend fun productsByCity(
        @Query("city") cityId: String,
        @Query("taste") taste: String
    ): ProductListModel



    @FormUrlEncoded
    @POST("calculate-checkout-distance")
    suspend fun calculateCheckoutDistance(
        @Field("address") address: String,
        @Field("product") productId: String,
    ): CalculateCheckoutDistanceModel



    @GET("all-products")
    suspend fun productsByBrand(
        @Query("brand") id: String,
        @Query("taste") taste: String
    ): ProductListModel


    @GET("all-products")
    suspend fun productsByCuisine(
        @Query("cuisine") id: String,
        @Query("taste") taste: String
    ): ProductListModel


    @GET("all-products")
    suspend fun productBySubcategory(
        @Query("sub_category") id: String,
        @Query("taste") taste: String
    ): ProductListModel

    @GET("all-products")
    suspend fun productByQuery(@Query("search") searchQuery: String): ProductListModel

    @POST("fetch-shop-by-slider")
    suspend fun productsBySlider(
        @Query("slider_name") name: String,
        @Query("taste") taste: String
    ): ProductListModel


    @GET("product-details")
    suspend fun productDetails(
        @Query("id") id: String,
        @Query("address") address: String
    ): ProductDetailsModel

    @GET("offer-deal")
    suspend fun getOfferByCity(@Query("city") cityId: String): CouponModel

    @GET("check-zipcode")
    suspend fun checkAvailability(
        @Query("zipcode") zipcode: String,
        @Query("vendor") id: String
    ): CheckAvailabilityModel

    @GET("cutofftime-check")
    suspend fun checkCutOffTimeCheck(
        @Query("start_city") startCity: String,
        @Query("end_city") endCity: String,
    ): CutOffTimeCheckModel
}