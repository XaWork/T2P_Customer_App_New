package me.taste2plate.app.customer.data.repo

import me.taste2plate.app.customer.data.api.UserApi
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
import me.taste2plate.app.customer.domain.model.user.GharKaKhanaCheckoutModel
import me.taste2plate.app.customer.domain.model.user.GharKaKhanaFetchCartModel
import me.taste2plate.app.customer.domain.model.user.MyPlanModel
import me.taste2plate.app.customer.domain.model.user.OrderConfirmModel
import me.taste2plate.app.customer.domain.model.user.OrderListModel
import me.taste2plate.app.customer.domain.model.user.OrderUpdateModel
import me.taste2plate.app.customer.domain.model.user.WalletTransactionModel
import me.taste2plate.app.customer.domain.model.user.WishListModel
import me.taste2plate.app.customer.domain.model.user.address.AddressListModel
import me.taste2plate.app.customer.domain.repo.UserRepo
import javax.inject.Inject

class UserRepoImpl @Inject constructor(
    private val api: UserApi
) : UserRepo {
    override suspend fun login(
        mobile: String,
        token: String,
        device: String
    ) = api.login(
        mobile = mobile,
        token = token,
        device = device,
    )

    override suspend fun verifyOTP(
        mobile: String,
        otp: String,
        token: String
    ) =
        api.verifyOtp(
            mobile = mobile,
            deviceToken = token,
            otp = otp
        )

    override suspend fun getProfile(id: String): GetProfileModel {
        return api.getProfile(id)
    }

    override suspend fun deleteUser(id: String): CommonResponse {
        return api.deleteUser(id)
    }

    override suspend fun getMyPlan(id: String): MyPlanModel {
        return api.getMyPlan(id)
    }

    override suspend fun getWalletTransaction(id: String): WalletTransactionModel {
        return api.getWalletTransactions(id)
    }

    override suspend fun editProfile(
        id: String,
        fullName: String,
        mobile: String,
        email: String
    ): CommonResponse {
        return api.editProfile(id, fullName, mobile, email)
    }

    override suspend fun getWishlist(userId: String) = api.getWishlist(userId)

    override suspend fun addToWishlist(userId: String, productId: String) =
        api.addToWishlist(userId, productId)

    override suspend fun addToCart(userId: String, pId: String, quantity: Int): CommonResponse {
        return api.addToCart(userId, pId, quantity)
    }

    override suspend fun deleteFromWishlist(
        userId: String,
        productId: String
    ) = api.deleteFromWishlist(userId, productId)

    override suspend fun getCart(userId: String, city: String?, zip: String?) =
        api.getCart(userId, city, zip)

    override suspend fun updateCart(
        userId: String,
        productID: String,
        quantity: Int
    ) = api.updateCart(userId, productID, quantity)

    override suspend fun deleteCart(userId: String, productID: String) =
        api.deleteCart(userId, productID)


    override suspend fun allAddress(userId: String): AddressListModel {
        return api.allAddress(userId)
    }

    override suspend fun deleteAddress(addressId: String): CommonResponse {
        return api.deleteAddress(addressId)
    }

    override suspend fun addAddress(
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
    ): CommonResponse {
        return api.addAddress(
            userId,
            name,
            phone,
            city,
            state,
            pincode,
            postOffice,
            addressLine,
            secondary,
            lat,
            lng,
            type
        )
    }

    override suspend fun editAddress(
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
    ): CommonResponse {
        return api.editAddress(
            addressId,
            name,
            phone,
            city,
            state,
            pincode,
            postOffice,
            addressLine,
            secondary,
            lat,
            lng,
            type
        )
    }

    override suspend fun getOrders(userId: String): OrderListModel {
        return api.getOrders(userId)
    }

    override suspend fun cancelOrder(orderId: String): CommonResponse {
        return api.cancelOrder(orderId)
    }

    override suspend fun getOrderUpdates(orderId: String): OrderUpdateModel {
        return api.getOrderUpdates(orderId)
    }

    override suspend fun createBulkOrder(
        name: String,
        email: String,
        mobile: String,
        city: String,
        address: String,
        msg: String
    ): CommonResponse {
        return api.createBulkOrder(name, email, mobile, city, address, msg)
    }

    override suspend fun applyCoupon(
        coupon: String,
        userid: String,
        cityId: String,
        zipCode: String
    ): ApplyCouponModel {
        return api.applyCoupon(coupon, userid, cityId, zipCode)
    }

    override suspend fun postReview(
        userId: String,
        productId: String,
        name: String,
        email: String,
        mobile: String,
        rating: Float,
        reviewText: String
    ): CommonResponse {
        return api.postReview(userId, productId, name, email, mobile, rating, reviewText)
    }

    override suspend fun initCheckout(
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
        browser: String
    ): CheckoutModel {
        return api.initCheckout(
            walletDiscount,
            userId,
            addressId,
            timeSlot,
            date,
            deliveryCost,
            express,
            couponCode,
            couponType,
            couponAmount,
            cartPrice,
            tipPrice,
            finalPrice,
            customerCity,
            addCost,
            zip,
            browser
        )
    }

    override suspend fun confirmOrder(
        isWalletApplied: Boolean,
        gateWay: String,
        orderId: String,
        transactionId: String
    ): OrderConfirmModel {
        return api.confirmOrder(isWalletApplied, gateWay, orderId, transactionId)
    }

    override suspend fun gharKaKhanaAddToCart(
        userId: String,
        product: String,
        category: String,
        subCategory: String,
        weight: String
    ): GharKaKhanaAddToCartModel {
        return api.gharKaKhanaAddToCart(userId, product, category, subCategory, weight)
    }

    override suspend fun gharKaKhanaFetchCart(userId: String): GharKaKhanaFetchCartModel {
        return api.gharKaKhanaFetchCart(userId)
    }

    override suspend fun gharKaKhanaDeleteCart(itemId: String): CommonResponse {
        return api.gharKaKhanaDeleteCart(itemId)
    }

    override suspend fun gharKaKhanaCheckout(
        userId: String,
        sourceLocation: String,
        destinationLocation: String,
        pickupDate: String,
        pickupTime: String,
        deliveryType: String
    ): GharKaKhanaCheckoutModel {
        return api.gharKaKhanaCheckout(
            userId,
            sourceLocation,
            destinationLocation,
            pickupDate,
            pickupTime,
            deliveryType
        )
    }

    override suspend fun gharKaKhanaConfirmCheckout(orderId: String): CommonResponse {
        return api.gharKaKhanaConfirmCheckout(orderId)
    }
}