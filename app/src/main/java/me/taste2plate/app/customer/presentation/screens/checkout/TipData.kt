package me.taste2plate.app.customer.presentation.screens.checkout

data class TipData(
    val tipPrice: String = "",
    var selected: Boolean = false,
    var mostTipped: Boolean = false,
)