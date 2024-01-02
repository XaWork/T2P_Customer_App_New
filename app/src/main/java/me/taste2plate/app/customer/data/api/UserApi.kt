package me.taste2plate.app.customer.data.api

import me.taste2plate.app.customer.domain.model.ApplyCouponModel
import me.taste2plate.app.customer.domain.model.auth.LoginModel
import me.taste2plate.app.customer.domain.model.auth.VerifyOTPModel
import me.taste2plate.app.customer.domain.model.product.CalculateCheckoutDistanceModel
import me.taste2plate.app.customer.domain.model.user.CartModel
import me.taste2plate.app.customer.domain.model.user.CheckoutModel
import me.taste2plate.app.customer.domain.model.user.CommonResponse
import me.taste2plate.app.customer.domain.model.user.DeleteFromWishlistModel
import me.taste2plate.app.customer.domain.model.user.GetProfileModel
import me.taste2plate.app.customer.domain.model.user.GharKaKhanaAddToCartModel
import me.taste2plate.app.customer.domain.model.user.GharKaKhanaFetchCartModel
import me.taste2plate.app.customer.domain.model.user.MyPlanModel
import me.taste2plate.app.customer.domain.model.user.OrderConfirmModel
import me.taste2plate.app.customer.domain.model.user.OrderListModel
import me.taste2plate.app.customer.domain.model.user.OrderUpdateModel
import me.taste2plate.app.customer.domain.model.user.WalletTransactionModel
import me.taste2plate.app.customer.domain.model.user.WishListModel
import me.taste2plate.app.customer.domain.model.user.address.AddressListModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {

    @FormUrlEncoded
    @POST("new-login")
    suspend fun login(
        @Field("mobile") mobile: String,
        @Field("device_token") token: String,
        @Field("device_type") device: String,
        @Field("reffer_by") referBy: String = "",
    ): LoginModel


    @FormUrlEncoded
    @POST("verify-otp")
    suspend fun verifyOtp(
        @Field("mobile") mobile: String,
        @Field("otp") otp: String,
        @Field("device_token") deviceToken: String
    ): VerifyOTPModel


    @FormUrlEncoded
    @POST("edit-profile")
    suspend fun editProfile(
        @Field("id") id: String,
        @Field("full_name") fullName: String,
        @Field("mobile") mobile: String,
        @Field("email") email: String,
    ): CommonResponse


    @GET("get-profile")
    suspend fun getProfile(
        @Query("id") id: String,
    ): GetProfileModel


    @GET("get-wallet-data")
    suspend fun getMyPlan(@Query("id") userId: String): MyPlanModel


    @POST("wallet-transactions")
    suspend fun getWalletTransactions(@Query("id") userId: String): WalletTransactionModel


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


    @FormUrlEncoded
    @POST("add-to-cart")
    suspend fun addToCart(
        @Field("id") userId: String,
        @Field("productid") pId: String,
        @Field("quantity") quantity: Int
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

    //----------------- Order ---------------------
    @GET("my-orders")
    suspend fun getOrders(@Query("id") userId: String): OrderListModel

    @FormUrlEncoded
    @POST("cancel-order")
    suspend fun cancelOrder(@Field("id") orderId: String): CommonResponse

    @POST("bulk-order")
    @FormUrlEncoded
    suspend fun createBulkOrder(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("mobile") mobile: String,
        @Field("city") city: String,
        @Field("address") address: String,
        @Field("msg") msg: String,
    ): CommonResponse

    @GET("order-updates")
    suspend fun getOrderUpdates(@Query("id") orderId: String): OrderUpdateModel

    //------------------ Checkout ---------------------
    @FormUrlEncoded
    @POST("apply-coupon")
    suspend fun applyCoupon(
        @Field("coupon") coupon: String,
        @Field("userid") userid: String,
        @Field("customer_city") cityId: String,
        @Field("customer_zip") zipCode: String,
    ): ApplyCouponModel


    //----------------- Product -----------

    @FormUrlEncoded
    @POST("add-review")
    suspend fun postReview(
        @Field("user") userId: String,
        @Field("product") productId: String,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("mobile") mobile: String,
        @Field("rating") rating: Float,
        @Field("review") reviewText: String,
    ): CommonResponse

    @FormUrlEncoded
    @POST("checkout")
    suspend fun initCheckout(
        @Field("wallet_discount") walletDiscount: Boolean,
        @Field("userid") userId: String,
        @Field("address") addressId: String,
        @Field("timeslot") timeSlot: String,
        @Field("delivery_date") date: String,
        @Field("shipping_price") deliveryCost: String,
        @Field("express") express: String,
        @Field("coupon") couponCode: String,
        @Field("coupontype") couponType: String,
        @Field("couponamount") couponAmount: String,
        @Field("price") cartPrice: String,
        @Field("tip_price") tipPrice: String,
        @Field("final_price") finalPrice: String,
        @Field("customer_city") customerCity: String,
        @Field("additional_cost") addCost: String,
        @Field("customer_zip") zip: String,
        @Field("browser") browser: String,
    ): CheckoutModel

    @FormUrlEncoded
    @POST("checkout-confirm")
    suspend fun confirmOrder(
        @Field("wallet_discount") isWalletApplied: Boolean,
        @Field("gateway") gateWay: String,
        @Field("orderid") orderId: String,
        @Field("transactionid") transactionId: String,
    ): OrderConfirmModel

    //-------------------- Ghar Ka Kahna -------------------

    @FormUrlEncoded
    @POST("gharkakhana-add-to-cart")
    suspend fun gharKaKhanaAddToCart(
        @Field("userId") userId: String,
        @Field("product") product: String,
        @Field("category") category: String,
        @Field("sub_category") subCategory: String,
        @Field("weight") weight: String,
    ): GharKaKhanaAddToCartModel

    @FormUrlEncoded
    @POST("gharkakhana-fetch-cart")
    suspend fun gharKaKhanaFetchCart(
        @Field("user") userId: String,
    ): GharKaKhanaFetchCartModel
    @FormUrlEncoded
    @POST("gharkakhana-delete-cart")
    suspend fun gharKaKhanaDeleteCart(
        @Field("id") itemId: String,
    ): CommonResponse
}