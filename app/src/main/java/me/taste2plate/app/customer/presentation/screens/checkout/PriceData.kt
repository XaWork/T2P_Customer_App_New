package me.taste2plate.app.customer.presentation.screens.checkout

import me.taste2plate.app.customer.presentation.utils.rupeeSign

data class PriceData(
    val title: String,
    val isWeight: Boolean = false,
    val subTitle: String? = null,
    val price: String,
    val sign: String = rupeeSign
)
