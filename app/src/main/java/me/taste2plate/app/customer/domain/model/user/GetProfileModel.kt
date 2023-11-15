package me.taste2plate.app.customer.domain.model.user


import com.google.gson.annotations.SerializedName
import me.taste2plate.app.customer.domain.model.auth.User

data class GetProfileModel(
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: User,
    @SerializedName("status")
    val status: String
)