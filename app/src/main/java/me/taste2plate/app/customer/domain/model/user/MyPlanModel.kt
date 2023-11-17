package me.taste2plate.app.customer.domain.model.user


import com.google.gson.annotations.SerializedName

data class MyPlanModel(
    @SerializedName("customer_point")
    val customerPoint: Int,
    @SerializedName("plan")
    val plan: Plan,
    @SerializedName("point_settings")
    val pointSettings: PointSettings
) {
    data class Plan(
        @SerializedName("cart_max_price")
        val cartMaxPrice: Int,
        @SerializedName("cart_min_price")
        val cartMinPrice: Int,
        @SerializedName("discount_percentage")
        val discountPercentage: Int,
        @SerializedName("exp_date")
        val expDate: String,
        @SerializedName("plan_expired")
        val planExpired: Boolean,
        @SerializedName("plan_name")
        val planName: String,
        @SerializedName("plan_price")
        val planPrice: String
    )

    data class PointSettings(
        @SerializedName("max_redeem_point_per_order")
        val maxRedeemPointPerOrder: String,
        @SerializedName("one_point_value_in_rupess")
        val onePointValueInRupess: String,
        @SerializedName("point_redeem_maximum_order_value")
        val pointRedeemMaximumOrderValue: String,
        @SerializedName("point_redeem_minimum_order_value")
        val pointRedeemMinimumOrderValue: String
    )

    val walletBalance:String
        get() = "₹ ${customerPoint * pointSettings.onePointValueInRupess.toFloat()}"
}