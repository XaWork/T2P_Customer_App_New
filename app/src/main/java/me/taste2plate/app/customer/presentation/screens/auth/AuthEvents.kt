package me.taste2plate.app.customer.presentation.screens.auth

import me.taste2plate.app.customer.domain.model.custom.LogRequest

sealed class AuthEvents {
    object Login : AuthEvents()
    data class AddLog(val logRequest: LogRequest) : AuthEvents()
    object VerifyOTP : AuthEvents()
    object GetUser : AuthEvents()
    object ResendOTP : AuthEvents()
    object SignUp : AuthEvents()
    object UpdateState : AuthEvents()
}
