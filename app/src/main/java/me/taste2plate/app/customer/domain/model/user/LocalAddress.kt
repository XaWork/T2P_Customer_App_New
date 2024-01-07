package me.taste2plate.app.customer.domain.model.user

data class LocalAddress(
    val pinCode: String,
    val lat: String,
    val lng: String,
    val cityId: String? = null,
    val cityName: String? = null
)
