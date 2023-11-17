package me.taste2plate.app.customer.domain.model.custom

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AllPlanListModel(
    @SerializedName("result")
    val plans: List<Plan>,
    @SerializedName("status")
    val status: String
)


@Keep
data class Plan(
    @SerializedName("active")
    val active: Int,
    @SerializedName("city")
    val city: List<City>,
    @SerializedName("created_date")
    val createdDate: String,
    @SerializedName("day")
    val day: Int,
    @SerializedName("deleted")
    val deleted: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("discount")
    val discount: Int,
    @SerializedName("_id")
    val id: String,
    @SerializedName("max_price")
    val maxPrice: Int,
    @SerializedName("min_price")
    val minPrice: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("point")
    val point: Int,
    @SerializedName("price")
    val price: Int,
    @SerializedName("update_date")
    val updateDate: String,
    @SerializedName("__v")
    val v: Int
){
    val itemPrice:String
        get() = "\u20B9 $price"

    val validityText:String
        get() = "Validity\n$day"

    val benefits:String
        get() = buildString {
            if(point>0) appendLine("\u2022 Points : $point")
            if(maxPrice>0) appendLine("\u2022 Max Price : ₹ $maxPrice")
            if(minPrice>0) appendLine("\u2022 Min Price : ₹ $minPrice")
            if(discount>0) append("\u2022 Discount : $discount%")
        }
}