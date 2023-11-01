package me.taste2plate.app.customer.data.repo

import me.taste2plate.app.customer.data.api.UserApi
import me.taste2plate.app.customer.domain.model.auth.LoginModel
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
}