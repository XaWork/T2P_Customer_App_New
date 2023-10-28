package me.taste2plate.app.customer.presentation.screens.checkout

import me.taste2plate.app.customer.presentation.utils.rupeeSign

data class PriceData(
    val title: String,
    val price: String,
    val sign: String = rupeeSign
)
