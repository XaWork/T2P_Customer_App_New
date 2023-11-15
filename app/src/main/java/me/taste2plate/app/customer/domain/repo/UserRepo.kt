package me.taste2plate.app.customer.domain.repo

import me.taste2plate.app.customer.domain.model.auth.LoginModel
import me.taste2plate.app.customer.domain.model.auth.VerifyOTPModel
import me.taste2plate.app.customer.domain.model.user.CartModel
import me.taste2plate.app.customer.domain.model.user.CommonResponse
import me.taste2plate.app.customer.domain.model.user.DeleteFromWishlistModel
import me.taste2plate.app.customer.domain.model.user.GetProfileModel
import me.taste2plate.app.customer.domain.model.user.OrderListModel
import me.taste2plate.app.customer.domain.model.user.WishListModel
import me.taste2plate.app.customer.domain.model.user.address.AddressListModel

interface UserRepo {
    suspend fun login(
        mobile: String,
        token: String,
        device: String
    ): LoginModel

    suspend fun verifyOTP(
        mobile: String,
        otp: String,
        token: String
    ): VerifyOTPModel

    suspend fun getProfile(
        id: String,
    ): GetProfileModel

    suspend fun editProfile(
        id: String,
        fullName: String,
        mobile: String,
    ): CommonResponse

    suspend fun getWishlist(
        userId: String,
    ): WishListModel

    suspend fun addToWishlist(
        userId: String,
        productId: String,
    ): CommonResponse

    suspend fun deleteFromWishlist(
        userId: String,
        productId: String,
    ): DeleteFromWishlistModel


    suspend fun addToCart(
        userId: String,
        pId: String,
        quantity: Int
    ): CommonResponse

    suspend fun getCart(
        userId: String,
        city: String,
        zip: String
    ): CartModel

    suspend fun updateCart(
        userId: String,
        productID: String,
        quantity: Int
    ): CommonResponse

    suspend fun deleteCart(
        userId: String,
        productID: String,
    ): CommonResponse

    suspend fun allAddress(
        userId: String,
    ): AddressListModel

    suspend fun deleteAddress(
        addressId: String,
    ): CommonResponse

    suspend fun addAddress(
        userId: String,
        name: String,
        phone: String,
        city: String,
        state: String,
        pincode: String,
        postOffice: String,
        addressLine: String,
        secondary: String,
        lat: Double,
        lng: Double,
        type: String
    ): CommonResponse

    suspend fun editAddress(
        addressId: String,
        name: String,
        phone: String,
        city: String,
        state: String,
        pincode: String,
        postOffice: String,
        addressLine: String,
        secondary: String,
        lat: Double,
        lng: Double,
        type: String
    ): CommonResponse

    suspend fun getOrders(userId: String): OrderListModel
    suspend fun createBulkOrder(
        name:String,
        email:String,
        mobile:String,
        city:String,
        address:String,
        msg:String,
    ): CommonResponse
}