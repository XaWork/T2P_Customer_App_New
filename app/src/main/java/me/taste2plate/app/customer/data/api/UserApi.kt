package me.taste2plate.app.customer.data.api

import me.taste2plate.app.customer.domain.model.auth.LoginModel
import me.taste2plate.app.customer.domain.model.auth.VerifyOTPModel
import me.taste2plate.app.customer.domain.model.user.CartModel
import me.taste2plate.app.customer.domain.model.user.CommonResponse
import me.taste2plate.app.customer.domain.model.user.DeleteFromWishlistModel
import me.taste2plate.app.customer.domain.model.user.WishListModel
import me.taste2plate.app.customer.domain.model.user.address.AddressListModel
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


    // ------------------------- Wishlist -------------------------
    @GET("get-wish")
    suspend fun getWishlist(
        @Query("id") userId: String
    ): WishListModel


    @FormUrlEncoded
    @POST("add-to-wish")
    suspend fun addToWishlist(
        @Field("id") userId: String,
        @Field("productid") productId: String
    ): CommonResponse

    @GET("delete-wish")
    suspend fun deleteFromWishlist(
        @Query("userid") userId: String,
        @Query("id") productId: String
    ): DeleteFromWishlistModel


    // ------------------------- Cart -------------------------

    @GET("get-cart")
    suspend fun getCart(
        @Query("id") id: String,
        @Query("customer_city") cityId: String,
        @Query("customer_zip") zipCode: String
    ): CartModel



    @FormUrlEncoded
    @POST("update-cart")
    suspend fun updateCart(
        @Field("userid") userId: String,
        @Field("id") productID: String,
        @Field("quantity") quantity: Int
    ): CommonResponse


    @GET("delete-cart")
    suspend fun deleteCart(
        @Query("userid") userId: String,
        @Query("id") productId: String
    ): CommonResponse


    //-------------------------- Address ----------------------
    @GET("all-address")
    suspend fun allAddress(@Query("id") userId: String): AddressListModel

    @GET("delete-address")
    suspend fun deleteAddress(@Query("id") id: String): CommonResponse

    @FormUrlEncoded
    @POST("add-address")
    suspend fun addAddress(
        @Field("userid") userId: String,
        @Field("contact_name") name: String,
        @Field("contact_mobile") phone: String,
        @Field("city") city: String,
        @Field("state") state: String,
        @Field("pincode") pincode: String,
        @Field("landmark") postOffice: String,
        @Field("address") addressLine: String,
        @Field("address2") secondary: String,
        @Field("lat") lat: Double,
        @Field("lng") lng: Double,
        @Field("title") type: String
    ): CommonResponse

    @FormUrlEncoded
    @POST("edit-address")
    suspend fun editAddress(
        @Field("id") addressId: String,
        @Field("contact_name") name: String,
        @Field("contact_mobile") phone: String,
        @Field("city") city: String,
        @Field("state") state: String,
        @Field("pincode") pincode: String,
        @Field("landmark") postOffice: String,
        @Field("address") addressLine: String,
        @Field("address2") secondary: String,
        @Field("lat") lat: Double,
        @Field("lng") lng: Double,
        @Field("title") type: String
    ): CommonResponse
}