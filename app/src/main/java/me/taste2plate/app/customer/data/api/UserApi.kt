package me.taste2plate.app.customer.data.api

import me.taste2plate.app.customer.domain.model.auth.LoginModel
import me.taste2plate.app.customer.domain.model.auth.VerifyOTPModel
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

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
}