package me.taste2plate.app.customer.domain.model.product


import com.google.gson.annotations.SerializedName
import me.taste2plate.app.customer.domain.model.HomeModel

data class ProductBySliderModel(
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: List<HomeModel.TopMostOrderedProduct>,
    @SerializedName("status")
    val status: String
)