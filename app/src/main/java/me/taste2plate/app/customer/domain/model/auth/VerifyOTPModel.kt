package me.taste2plate.app.customer.domain.model.auth


import com.google.gson.annotations.SerializedName

data class VerifyOTPModel(
    @SerializedName("data")
    val `data`: User,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)