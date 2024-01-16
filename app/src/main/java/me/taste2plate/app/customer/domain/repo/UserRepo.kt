package me.taste2plate.app.customer.domain.repo

import me.taste2plate.app.customer.domain.model.ApplyCouponModel
import me.taste2plate.app.customer.domain.model.auth.LoginModel
import me.taste2plate.app.customer.domain.model.auth.VerifyOTPModel
import me.taste2plate.app.customer.domain.model.user.CartModel
import me.taste2plate.app.customer.domain.model.user.CheckoutModel
import me.taste2plate.app.customer.domain.model.user.CommonResponse
import me.taste2plate.app.customer.domain.model.user.DeleteFromWishlistModel
import me.taste2plate.app.customer.domain.model.user.GetProfileModel
import me.taste2plate.app.customer.domain.model.user.GharKaKhanaAddToCartModel
import me.taste2plate.app.customer.domain.model.user.GharKaKhanaCheckoutModel
import me.taste2plate.app.customer.domain.model.user.GharKaKhanaFetchCartModel
import me.taste2plate.app.customer.domain.model.user.MyPlanModel
import me.taste2plate.app.customer.domain.model.user.OrderConfirmModel
import me.taste2plate.app.customer.domain.model.user.OrderListModel
import me.taste2plate.app.customer.domain.model.user.OrderUpdateModel
import me.taste2plate.app.customer.domain.model.user.WalletTransactionModel
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

    suspend fun getMyPlan(
        id: String,
    ): MyPlanModel

    suspend fun deleteUser(
        id: String,
    ): CommonResponse

    suspend fun getWalletTransaction(
        id: String,
    ): WalletTransactionModel

    suspend fun editProfile(
        id: String,
        fullName: String,
        mobile: String,
        email: String,
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
        city: String?,
        zip: String?
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
    suspend fun cancelOrder(orderId: String): CommonResponse
    suspend fun getOrderUpdates(orderId: String): OrderUpdateModel
    suspend fun createBulkOrder(
        name: String,
        email: String,
        mobile: String,
        city: String,
        address: String,
        msg: String,
    ): CommonResponse

    suspend fun postReview(
        userId: String,
        productId: String,
        name: String,
        email: String,
        mobile: String,
        rating: Float,
        reviewText: String,
    ): CommonResponse

    suspend fun applyCoupon(
        coupon: String,
        userid: String,
        cityId: String,
        zipCode: String,
    ): ApplyCouponModel

    suspend fun initCheckout(
        walletDiscount: Boolean,
        userId: String,
        addressId: String,
        timeSlot: String,
        date: String,
        deliveryCost: String,
        express: String,
        couponCode: String,
        couponType: String,
        couponAmount: String,
        cartPrice: String,
        tipPrice: String,
        finalPrice: String,
        customerCity: String,
        addCost: String,
        zip: String,
        browser: String,
    ): CheckoutModel

    suspend fun confirmOrder(
        isWalletApplied: Boolean,
        gateWay: String,
        orderId: String,
        transactionId: String,
    ): OrderConfirmModel

    suspend fun gharKaKhanaAddToCart(
        userId: String,
        product: String,
        category: String,
        subCategory: String,
        weight: String,
    ): GharKaKhanaAddToCartModel

    suspend fun gharKaKhanaFetchCart(
        userId: String,
    ): GharKaKhanaFetchCartModel

    suspend fun gharKaKhanaDeleteCart(
        itemId: String,
    ): CommonResponse

    suspend fun gharKaKhanaConfirmCheckout(
        userId: String,
        sourceLocation: String,
        destinationLocation: String,
        pickupDate: String,
        pickupTime: String,
        deliveryType: String
    ): CommonResponse

    suspend fun gharKaKhanaCheckout(
        userId: String,
        sourceLocation: String,
        destinationLocation: String,
        pickupDate: String,
        pickupTime: String,
        deliveryType: String,
    ): GharKaKhanaCheckoutModel
}