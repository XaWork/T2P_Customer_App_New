package me.taste2plate.app.customer.domain.model.auth


import com.google.gson.annotations.SerializedName

data class LoginModel(
    @SerializedName("message")
    val message: String,
    @SerializedName("OTP")
    val oTP: String,
    @SerializedName("status")
    val status: String
)