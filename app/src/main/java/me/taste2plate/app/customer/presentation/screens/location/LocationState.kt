package me.taste2plate.app.customer.presentation.screens.location

import android.location.Location

data class LocationState(
    val isLoading: Boolean = false,
    val addressSavedLocally: Boolean = false,
    val error: String? = null,
    val location: Location? = null,
)
