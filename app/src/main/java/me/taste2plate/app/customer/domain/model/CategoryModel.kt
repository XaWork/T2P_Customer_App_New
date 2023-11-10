package me.taste2plate.app.customer.domain.model


import com.google.gson.annotations.SerializedName

data class CategoryModel(
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: List<Category>,
    @SerializedName("status")
    val status: String
)