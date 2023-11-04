package me.taste2plate.app.customer.domain.model.user


import com.google.gson.annotations.SerializedName

data class CommonResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("qty")
    val qty: Int,
    @SerializedName("status")
    val status: String
)