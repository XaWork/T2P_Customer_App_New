package me.taste2plate.app.customer.data.api

import me.taste2plate.app.customer.domain.model.auth.LoginModel
import me.taste2plate.app.customer.domain.model.auth.VerifyOTPModel
import me.taste2plate.app.customer.domain.model.user.CartModel
import me.taste2plate.app.customer.domain.model.user.WishListModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("mobile") mobile: String,
        @Field("device_token") token: String,
        @Field("device_type") device: String
    ): LoginModel


    @FormUrlEncoded
    @POST("verify-otp")
    suspend fun verifyOtp(
        @Field("mobile") mobile: String,
        @Field("otp") otp: String,
        @Field("device_token") deviceToken: String
    ): VerifyOTPModel


    @GET("get-wish")
    suspend fun getWishlist(
        @Query("id") userId: String
    ): WishListModel



    @GET("get-cart")
    suspend fun getCart(
        @Query("id") id: String,
        @Query("customer_city") cityId: String,
        @Query("customer_zip") zipCode: String
    ): CartModel
}