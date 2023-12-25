package me.taste2plate.app.customer.presentation.screens.profile

import me.taste2plate.app.customer.domain.model.custom.LogRequest

sealed class ProfileEvents {
    object EditProfile : ProfileEvents()
    object UpdateState : ProfileEvents()
    data class AddLog(
        val logRequest: LogRequest
    ) : ProfileEvents()
}
