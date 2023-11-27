package me.taste2plate.app.customer.domain.model.user

import androidx.annotation.Keep

@Keep
data class CheckoutModel(
    val message: String,
    val orderId: String,
    val status: String
)