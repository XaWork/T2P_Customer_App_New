package me.taste2plate.app.customer.presentation.screens.profile

sealed class ProfileEvents {
    object EditProfile : ProfileEvents()
    object UpdateState : ProfileEvents()
}
