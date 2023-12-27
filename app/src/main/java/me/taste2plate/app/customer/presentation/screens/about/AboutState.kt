package me.taste2plate.app.customer.presentation.screens.about

import me.taste2plate.app.customer.domain.model.SettingsModel

data class AboutState(
    var isLoading: Boolean = false,
    var isError : Boolean = false,
    var setting: SettingsModel.Result? = null
)