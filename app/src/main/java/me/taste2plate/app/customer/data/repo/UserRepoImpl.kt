package me.taste2plate.app.customer.data.repo

import me.taste2plate.app.customer.data.api.UserApi
import me.taste2plate.app.customer.domain.model.ApplyCouponModel
import me.taste2plate.app.customer.domain.model.auth.LoginModel
import me.taste2plate.app.customer.domain.model.auth.VerifyOTPModel
import me.taste2plate.app.customer.domain.model.user.CartModel
import me.taste2plate.app.customer.domain.model.user.CommonResponse
import me.taste2plate.app.customer.domain.model.user.DeleteFromWishlistModel
import me.taste2plate.app.customer.domain.model.user.GetProfileModel
import me.taste2plate.app.customer.domain.model.user.MyPlanModel
import me.taste2plate.app.customer.domain.model.user.OrderListModel
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

    override suspend fun getMyPlan(id: String): MyPlanModel {
        return api.getMyPlan(id)
    }

    override suspend fun editProfile(id: String, fullName: String, mobile: String): CommonResponse {
        return api.editProfile(id, fullName, mobile)
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

    override suspend fun getCart(userId: String, city: String, zip: String) =
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
}