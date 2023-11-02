package me.taste2plate.app.customer.presentation.screens.home

import android.location.Location
import me.taste2plate.app.customer.domain.model.HomeModel

data class HomeState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isError: Boolean = false,
    val homeData: HomeModel? = null
)
