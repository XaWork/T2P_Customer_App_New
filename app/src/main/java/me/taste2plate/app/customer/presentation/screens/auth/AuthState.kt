package me.taste2plate.app.customer.presentation.screens.auth

import me.taste2plate.app.customer.domain.model.auth.LoginModel
import me.taste2plate.app.customer.domain.model.auth.VerifyOTPModel

data class AuthState(
    val loading:Boolean = false,
    val isError: Boolean = false,
    val message: String? = null,
    val loginModel: LoginModel? = null,
    val verifyOTPModel: VerifyOTPModel? = null,
)
