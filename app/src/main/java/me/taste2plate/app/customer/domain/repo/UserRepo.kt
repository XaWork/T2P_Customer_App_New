package me.taste2plate.app.customer.domain.repo

import me.taste2plate.app.customer.domain.model.auth.LoginModel
import me.taste2plate.app.customer.domain.model.auth.VerifyOTPModel
import me.taste2plate.app.customer.domain.model.user.CartModel
import me.taste2plate.app.customer.domain.model.user.CommonResponse
import me.taste2plate.app.customer.domain.model.user.DeleteFromWishlistModel
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
}