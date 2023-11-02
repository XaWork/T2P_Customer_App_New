package me.taste2plate.app.customer.presentation.screens.location

import android.content.Context

sealed class LocationEvent{
    data class GetCurrentLocation(
        val context: Context
    ): LocationEvent()
}
