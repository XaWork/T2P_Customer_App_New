package me.taste2plate.app.customer.domain.repo

import me.taste2plate.app.customer.domain.model.auth.LoginModel
import me.taste2plate.app.customer.domain.model.auth.VerifyOTPModel

interface UserRepo {
    suspend fun login(
        mobile: String,
        token: String,
        device: String): LoginModel

    suspend fun verifyOTP(
        mobile: String,
        otp: String,
        token: String): VerifyOTPModel
}