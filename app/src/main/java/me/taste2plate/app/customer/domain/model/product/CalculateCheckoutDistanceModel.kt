package me.taste2plate.app.customer.domain.model.product


import com.google.gson.annotations.SerializedName

data class CalculateCheckoutDistanceModel(
    @SerializedName("distance")
    val distance: Double,
    @SerializedName("message")
    val message: String,
    @SerializedName("office_home")
    val officeHome: Double,
    @SerializedName("office_office")
    val officeOffice: Double,
    @SerializedName("status")
    val status: String
)