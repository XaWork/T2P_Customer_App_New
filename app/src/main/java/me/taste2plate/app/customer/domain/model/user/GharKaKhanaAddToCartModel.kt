package me.taste2plate.app.customer.domain.model.user


import com.google.gson.annotations.SerializedName

data class GharKaKhanaAddToCartModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
) {
    data class Data(
        @SerializedName("category")
        val category: String,
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("height")
        val height: String,
        @SerializedName("_id")
        val id: String,
        @SerializedName("length")
        val length: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("price")
        val price: String,
        @SerializedName("sub_category")
        val subCategory: String,
        @SerializedName("updatedAt")
        val updatedAt: String,
        @SerializedName("user")
        val user: String,
        @SerializedName("__v")
        val v: Int,
        @SerializedName("weidth")
        val weidth: String,
        @SerializedName("weight")
        val weight: String
    )
}